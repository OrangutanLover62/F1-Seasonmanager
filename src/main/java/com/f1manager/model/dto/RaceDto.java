package com.f1manager.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Accessors(chain = true)
public class RaceDto {
    @Id
    private String id;
    private int laps;
    private TrackDto track;
    private SeasonDto season;
    private String html;
}
