package com.rgq.f1challenge.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class SeasonDto {
    @Id
    private String id;
    private String name;
    private List<RaceDto> races;
    private List<DriverDto> drivers;
    private String html;
}
