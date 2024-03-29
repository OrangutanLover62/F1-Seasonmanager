package com.f1manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeasonCreationDto {

    private String name;
    private List<RaceDto> races;
    private List<DriverDto> drivers;

}
