package com.f1manager.model.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "Drivers")
public class DriverDao {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String imageBase64;
}
