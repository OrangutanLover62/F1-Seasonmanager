package com.rgq.f1challenge.controller.rest;

import com.rgq.f1challenge.model.dto.RaceDto;
import com.rgq.f1challenge.model.dto.TrackDto;
import com.rgq.f1challenge.service.ErrorService;
import com.rgq.f1challenge.service.RaceService;
import com.rgq.f1challenge.service.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}
