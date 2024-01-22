package com.rgq.f1challenge.model.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "Races")
public class RaceDao {
    @Id
    private String id;
    private int laps;
    private TrackDao track;
    private SeasonDao season;
    private ResultDao result;
    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime finished;

}
