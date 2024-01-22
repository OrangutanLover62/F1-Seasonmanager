package com.rgq.f1challenge.repository;

import com.rgq.f1challenge.model.dao.SeasonDao;
import com.rgq.f1challenge.model.dao.TrackDao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeasonRepository extends MongoRepository<SeasonDao, String> {
}
