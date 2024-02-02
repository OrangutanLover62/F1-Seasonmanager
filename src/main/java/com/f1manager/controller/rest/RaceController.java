package com.f1manager.controller.rest;

import com.f1manager.model.dao.RaceDao;
import com.f1manager.model.dto.RaceDto;
import com.f1manager.model.dto.TrackDto;
import com.f1manager.repository.RaceRepository;
import com.f1manager.service.ErrorService;
import com.f1manager.service.TrackService;
import com.f1manager.service.RaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/race")
@AllArgsConstructor
public class RaceController {
    private final ErrorService errorService;
    private final TrackService trackService;
    private final RaceService raceService;

    @GetMapping("/getAllRaces")
    public ResponseEntity<Object> getAllRaces() {
        try {
            trackService.updateByHtml();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return errorService.handle(e);
        }
    }

    @PostMapping("/creationEditor")
    public ResponseEntity<Object> createRace(
            @RequestBody TrackDto track
    ){
        try {
            RaceDto newRace = new RaceDto();
            newRace.setId(UUID.randomUUID().toString());
            newRace.setTrack(track);
            return ResponseEntity.ok().body(raceService.buildEditorHtml(newRace));
        } catch (Exception e) {
            return errorService.handle(e);
        }
    }

    @PostMapping("/generateById")
    public RaceDto generateRaceDto(
            @RequestParam(required = true) String id,
            @RequestParam(required = true) Integer laps,
            @RequestBody TrackDto track
    ) {
        return new RaceDto()
                .setId(id)
                .setLaps(laps)
                .setTrack(track);
    }

    @GetMapping("/findById")
    public RaceDto getById(
            @RequestParam(required = true) String id
    ) {
        try {

            return raceService.findById(id);
        } catch (Exception e) {
            return null;
        }
    }
}
