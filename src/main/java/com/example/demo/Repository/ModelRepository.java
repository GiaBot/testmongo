package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.models.Model;

public interface ModelRepository extends MongoRepository<Model, String> {

    @Query(value="{ '_id': ?0}", fields="{ 'modells': 1 }")
    List<Model> findModellsByIds(List<String> ids);

}
