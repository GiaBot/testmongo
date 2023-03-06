package com.example.demo.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.demo.models.SchemalessModel;

public interface SchemalessModelRepo extends MongoRepository<SchemalessModel, String> {

    @Query(value="{ '_id': ?0 }", fields="{ 'additionalData.?1': 1 }")
    List<SchemalessModel> getAdditionalData(ObjectId id, String key);

    @Query("{ 'name': ?0 }")
    List<SchemalessModel> findByName(String name);

    @Query("{ '_id': ?0 }")
    @Update("{ $set: { 'additionalData.?1': ?2 } }")
    void setAdditionalData(ObjectId id, String key, String[] values);

    @Query("{ '_id': ?0 }")
    @Update("{ $push: { 'additionalData.?1': ?2} }")
    void addAdditionalData(ObjectId id, String key, String value);

    @Query("{ '_id': ?0 }")
    @Update("{ $pull: { 'additionalData.?1': { $in: ?2 }}}")
    void removeAdditionalData(ObjectId id, String key, String[] values);

    @Query("{ '_id': ?0 }")
    @Update("{ $unset: {'additionalData.?1': 1 }}")
    void removeAdditionalDataField(ObjectId id, String key);

    @Query("{}")
    @Update("{ $mul: {'price': ?0} }")
    void setPrices(double multiplier);

    @Query("{ _id: ?0}")
    List<SchemalessModel> getModelById(ObjectId id);

}
