package com.rgq.f1challenge.model.dto;

import com.rgq.f1challenge.model.dao.ResultDao;
import com.rgq.f1challenge.model.dao.SeasonDao;
import com.rgq.f1challenge.model.dao.TrackDao;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

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
