package com.rgq.f1challenge.controller.rest;

import com.rgq.f1challenge.model.dto.DriverDto;
import com.rgq.f1challenge.model.dto.RaceDto;
import com.rgq.f1challenge.model.dto.SeasonCreationDto;
import com.rgq.f1challenge.model.dto.TrackDto;
import com.rgq.f1challenge.service.ErrorService;
import com.rgq.f1challenge.service.SeasonService;
import com.rgq.f1challenge.service.TrackService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/season")
@AllArgsConstructor
public class SeasonController {
    private final ErrorService errorService;
    private final SeasonService seasonService;



    @PostMapping("/create")
    public ResponseEntity<Object> createNewSeason(
            @RequestBody(required = true) SeasonCreationDto seasonDto
    )
    {
        try {
            seasonService.createSeason(seasonDto.getName(), seasonDto.getRaces(), seasonDto.getDrivers());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return errorService.handle(e);
        }
    }

}
