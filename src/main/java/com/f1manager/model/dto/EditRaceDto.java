package com.f1manager.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
@Setter
@Accessors(chain = true)
public class EditRaceDto {
    private RaceDto race;
    private TrackDto track;
    private ArrayList<DriverDto> drivers;
}
