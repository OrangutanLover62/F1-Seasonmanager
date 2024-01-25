package com.f1manager.model.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "Results")
public class ResultDao {
    @Id
    private String id;
    private String raceId;
    private Map<DriverDao, Integer> positions;
}
