package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.models.Model;

public interface ModelRepository extends MongoRepository<Model, String> {

    @Query("{ '_id': { '$in': ?0 } }")
    List<Model> findModellsByIds(List<String> ids);

}
