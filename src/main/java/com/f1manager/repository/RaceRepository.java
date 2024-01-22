package com.f1manager.repository;

import com.f1manager.model.dao.RaceDao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RaceRepository extends MongoRepository<RaceDao, String> {
}
