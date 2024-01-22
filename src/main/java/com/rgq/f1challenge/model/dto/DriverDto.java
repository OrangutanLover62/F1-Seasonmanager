package com.rgq.f1challenge.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DriverDto {
    private String id;
    private String firstname;
    private String lastname;
    private String imageBase64;
    private String html;
}
