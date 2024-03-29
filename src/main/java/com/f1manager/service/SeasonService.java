package com.f1manager.service;

import com.f1manager.model.dao.DriverDao;
import com.f1manager.model.dao.RaceDao;
import com.f1manager.model.dao.SeasonDao;
import com.f1manager.model.dto.TrackDto;
import com.f1manager.repository.SeasonRepository;
import com.f1manager.repository.TrackRepository;
import com.f1manager.model.dao.TrackDao;
import com.f1manager.model.dto.DriverDto;
import com.f1manager.model.dto.RaceDto;
import com.f1manager.model.dto.SeasonDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class SeasonService {
    private final TrackRepository trackRepository;
    private final SeasonRepository seasonRepository;
    private final ConfigurationService configurationService;
    private final ErrorService errorService;
    private final RestClientService restClientService;
    private final FragmentService fragmentService;
    private final RaceService raceService;
    private final DriverService driverService;


    public SeasonDao dtoDaoConverter(SeasonDto seasonDto) {
        List<RaceDao> convertedRaces = null;
        for (RaceDto race: seasonDto.getRaces()
        ) {
            convertedRaces.add(raceService.dtoDaoConverter(race));
        }

        List<DriverDao> convertedDrivers = null;
        for (DriverDto driver: seasonDto.getDrivers()
             ) {
            convertedDrivers.add(driverService.dtoDaoConverter(driver));
            }

        return new SeasonDao()
                .setId(seasonDto.getId())
                .setName(seasonDto.getName())
                .setRaces(convertedRaces)
                .setDrivers(convertedDrivers);
    }

    public SeasonDto daoDtoConverter(SeasonDao seasonDao) {
        List<RaceDto> convertedRaces = new ArrayList<>();
        for (RaceDao race : seasonDao.getRaces()
             ) {
            convertedRaces.add(raceService.daoDtoConverter(race));
        }

        List<DriverDto> convertedDrivers = new ArrayList<>();
        for (DriverDao driver : seasonDao.getDrivers()
        ) {
            convertedDrivers.add(driverService.daoDtoConverter(driver));
        }

        return buildHtml(new SeasonDto()
            .setId(seasonDao.getId())
            .setName(seasonDao.getName())
            .setRaces(convertedRaces)
            .setDrivers(convertedDrivers)
        );
    }

    public SeasonDto createSeason(String name, List<RaceDto> races, List<DriverDto> drivers){

        SeasonDao newSeason = new SeasonDao();
        newSeason.setId(UUID.randomUUID().toString());
        newSeason.setName(name);


        List<RaceDao> convertedRaces = new ArrayList<>();
        for (RaceDto race : races
        ) {
            RaceDao raceToAdd = raceService.dtoDaoConverter(race);
            raceToAdd.setSeasonId(newSeason.getId());
            convertedRaces.add(raceToAdd);
        }

        List<DriverDao> convertedDrivers = new ArrayList<>();
        for (DriverDto driver : drivers
        ) {
            convertedDrivers.add(driverService.dtoDaoConverter(driver));
        }

        newSeason.setRaces(convertedRaces);
        newSeason.setDrivers(convertedDrivers);
        seasonRepository.insert(newSeason);
        return daoDtoConverter(newSeason);
    }



    ///// Old Testing function

    public SeasonDto createNewSeason(String name, List<String> trackIds, List<Integer> laps, List<String> driverIds){
        SeasonDao newSeason = new SeasonDao();
        newSeason.setName(name);
        List<TrackDao> tracks = new ArrayList<>();
        for (String track : trackIds
             ) {
            TrackDao trackToAdd = trackRepository.findById(track).orElseThrow(() -> new IllegalArgumentException("Track not found for id: " + track));
            tracks.add(trackToAdd);
        }
        List<RaceDao> races = new ArrayList<>();
        for (TrackDao track : tracks
             ) {
            RaceDao raceToAdd = raceService.createRace(track, laps.get(tracks.indexOf(track)));
            races.add(raceToAdd);
        }
        newSeason.setRaces(races);

        List<DriverDao> drivers = new ArrayList<>();
        for (String driver : driverIds
             ) {
            drivers.add(driverService.dtoDaoConverter(driverService.findById(driver)));
        }
        newSeason.setDrivers(drivers);

        seasonRepository.save(newSeason);
        return daoDtoConverter(newSeason);
    }



    public SeasonDto findById(String id) {
        return daoDtoConverter(seasonRepository.findById(id).orElseThrow(null));
    }

    public List<SeasonDto> findAll() {
        return seasonRepository
            .findAll()
            .stream()
            .map(this::daoDtoConverter)
            .toList();
    }

    private SeasonDto buildHtml(SeasonDto seasonDto) {
        return seasonDto.setHtml(fragmentService.render(
            "seasonView", Collections.singletonMap("season", seasonDto)
        ));
    }

}
