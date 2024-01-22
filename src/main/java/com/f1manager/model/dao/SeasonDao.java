package com.f1manager.model.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "Seasons")
public class SeasonDao {
    @Id
    private String id;
    private String name;
    private List<RaceDao> races;
    private List<DriverDao> drivers;
    @CreatedDate
    @Field(targetType = FieldType.DATE_TIME)
    private LocalDateTime created;
}
