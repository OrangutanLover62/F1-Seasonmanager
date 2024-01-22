package com.f1manager.controller.rest;

import com.f1manager.service.ErrorService;
import com.f1manager.model.dto.SeasonCreationDto;
import com.f1manager.service.SeasonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
