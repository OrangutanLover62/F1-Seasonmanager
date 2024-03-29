package com.f1manager.service;

import com.f1manager.model.dao.RaceDao;
import com.f1manager.model.dao.TrackDao;
import com.f1manager.model.dto.RaceDto;
import com.f1manager.repository.RaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Lazy
@AllArgsConstructor
public class RaceService {
    private final RaceRepository raceRepository;
    private final ConfigurationService configurationService;
    private final ErrorService errorService;
    private final RestClientService restClientService;
    private final FragmentService fragmentService;
    private final TrackService trackService;


    public RaceDao dtoDaoConverter(RaceDto raceDto) {
        return new RaceDao()
            .setId(raceDto.getId())
            .setLaps(raceDto.getLaps())
            .setSeasonId(raceDto.getSeasonId())
            .setTrack(trackService.dtoDaoConverter(raceDto.getTrack()));
    }

    public RaceDto daoDtoConverter(RaceDao raceDao) {
        return buildHtml(new RaceDto()
            .setId(raceDao.getId())
            .setSeasonId(raceDao.getSeasonId())
            .setLaps(raceDao.getLaps())
            .setTrack(trackService.daoDtoConverter(raceDao.getTrack()))
        );
    }

    public RaceDao createRace(TrackDao track, int laps){
        RaceDao newRace = new RaceDao();
        newRace.setId(UUID.randomUUID().toString());
        newRace.setLaps(laps);
        newRace.setTrack(track);
        raceRepository.insert(newRace);

        return newRace;
    }

    public RaceDto buildEditorHtml(RaceDto raceDto) {
        return raceDto.setHtml(fragmentService.render(
                "raceCreationView", Collections.singletonMap("race", raceDto)
        ));
    }

    public RaceDto buildHtml(RaceDto raceDto) {
        return raceDto.setHtml(fragmentService.render(
            "raceView", Collections.singletonMap("race", raceDto)
        ));
    }

    public RaceDto findById(String id){
        RaceDao race = raceRepository.findById(id).orElse(null);
        return daoDtoConverter(raceRepository.findById(id).orElse(null)) ;
    }

    public void save(RaceDto race){

        raceRepository.save(dtoDaoConverter(race));

    }
}
