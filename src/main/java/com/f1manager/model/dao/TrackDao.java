package com.f1manager.model.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "Tracks")
public class TrackDao {
    @Id
    private String id;
    private String name;
    private String country;
    private String imageBase64;


}
