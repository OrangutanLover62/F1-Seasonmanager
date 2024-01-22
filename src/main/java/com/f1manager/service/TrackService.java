package com.f1manager.service;

import com.f1manager.model.dao.TrackDao;
import com.f1manager.model.dto.TrackDto;
import com.f1manager.model.exception.NoResultsException;
import com.f1manager.repository.TrackRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final ConfigurationService configurationService;
    private final ErrorService errorService;
    private final RestClientService restClientService;
    private final FragmentService fragmentService;

    public void updateByHtml() throws NoResultsException {
        Document document = Jsoup.parse(
            restClientService.getHtml(configurationService.getTrackUrl())
        );
        Elements elements = document.select(".wixui-gallery__item");
        if (elements.isEmpty()) {
            throw new NoResultsException("There are no elements to import");
        }
        // ToDo: Make a backup first
        trackRepository.deleteAll();
        importTracksByHtml(elements);
    }

    private void importTracksByHtml(Elements elements) {
        for (Element element : elements) {
            try {
                Element title = element.select("[data-testid=gallery-item-title]").first();
                Element img = element.select("img").first();
                String src = img.attr("src");
                int index = src.indexOf(".jpg");
                // Split the url to get the non-blurred image
                byte[] image = restClientService.getImage(src.substring(0, index + 4));
                String base64 = Base64.getEncoder().encodeToString(image);
                TrackDao track = new TrackDao()
                    .setName(title.text())
                    .setImageBase64("data:image/webp;base64," + base64);
                trackRepository.save(track);
            } catch (Exception e) {
                errorService.handle(e);
            }
        }
    }

    public TrackDao dtoDaoConverter(TrackDto trackDto) {
        return new TrackDao()
            .setName(trackDto.getName())
            .setImageBase64(trackDto.getImageBase64());
    }

    public TrackDto daoDtoConverter(TrackDao trackDao) {
        return buildHtml(new TrackDto()
            .setId(trackDao.getId())
            .setName(trackDao.getName())
            .setImageBase64(trackDao.getImageBase64())
        );
    }

    public TrackDto buildHtml(TrackDto trackDto) {
        return trackDto.setHtml(fragmentService.render(
            "trackView", Collections.singletonMap("track", trackDto)
        ));
    }

    public List<TrackDto> findAll() {
        return trackRepository
                .findAll()
                .stream()
                .map(this::daoDtoConverter)
                .toList();
    }

    public TrackDto findByName(String name) {
        Optional<TrackDao> optionalTrack = Optional.ofNullable(trackRepository.findByName(name));
        return optionalTrack.map(this::daoDtoConverter).orElse(null);
    }

    public TrackDto findById(String id){
        return trackRepository
                .findById(id)
                .map(this::daoDtoConverter)
                .orElse(null);
    }

}
