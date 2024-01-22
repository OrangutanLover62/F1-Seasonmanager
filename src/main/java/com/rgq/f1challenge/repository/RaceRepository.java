package com.rgq.f1challenge.repository;

import com.rgq.f1challenge.model.dao.RaceDao;
import com.rgq.f1challenge.model.dao.SeasonDao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RaceRepository extends MongoRepository<RaceDao, String> {
}
