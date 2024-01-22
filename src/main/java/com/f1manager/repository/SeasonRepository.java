package com.f1manager.repository;

import com.f1manager.model.dao.SeasonDao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeasonRepository extends MongoRepository<SeasonDao, String> {
}
