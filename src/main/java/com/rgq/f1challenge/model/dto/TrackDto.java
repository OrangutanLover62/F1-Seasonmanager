package com.rgq.f1challenge.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TrackDto {
    private String id;
    private String name;
    private String country;
    private String imageBase64;
    private String html;
}
