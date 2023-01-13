package com.example.demo.Repository;

import java.util.List;
// import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.models.Model;

public interface ModelRepository extends MongoRepository<Model, String> {

    @Query("{ '_id': { '$in': [?0] } }")
    public List<Model> findModellsByIds(List<ObjectId> ids);
    
}