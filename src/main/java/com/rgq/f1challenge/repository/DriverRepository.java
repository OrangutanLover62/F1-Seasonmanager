package com.rgq.f1challenge.repository;

import com.rgq.f1challenge.model.dao.DriverDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DriverRepository extends MongoRepository<DriverDao, String> {

    @Query("{$or: [ { 'firstname': { $regex: ?0, $options: 'i' } }, { 'lastname': { $regex: ?0, $options: 'i' } } ]}")
    List<DriverDao> findByName(String name);
}
