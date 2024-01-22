package com.f1manager.repository;

import com.f1manager.model.dao.TrackDao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackRepository extends MongoRepository<TrackDao, String> {
    TrackDao findByName(String name);
}
