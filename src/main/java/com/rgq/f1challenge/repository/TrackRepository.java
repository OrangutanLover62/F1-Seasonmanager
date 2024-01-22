package com.rgq.f1challenge.repository;

import com.rgq.f1challenge.model.dao.TrackDao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrackRepository extends MongoRepository<TrackDao, String> {
    TrackDao findByName(String name);
}
