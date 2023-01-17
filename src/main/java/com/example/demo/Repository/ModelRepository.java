package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.demo.models.Model;

public interface ModelRepository extends MongoRepository<Model, String> {

    @Query("{ '_id': { '$in': [?0] } }")
    List<Model> findModellsByIds(List<String> ids);

    // @Update()
    // List<Model> updateTotalPrices();

    // @Update(pipeline = {"{ $set: { 'size.?0': { $mul: {'size.?0': ?1} } } }"})
    // void findAllModelsAndUpdatePriceBySize(String size, float multiplier);

}
