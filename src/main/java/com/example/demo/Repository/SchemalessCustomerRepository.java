package com.example.demo.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.demo.models.SchemalessCustomer;


public interface SchemalessCustomerRepository extends MongoRepository<SchemalessCustomer, String>{
    
    @Query("{ '_id': ?0 }")
    List<SchemalessCustomer> findSchemalessCustomerById(ObjectId id);

    @Query("{ '_id': ?0 }")
    @Update("{$set: { ?1: ?2 }}")
    void findAndUpdateSchemalessCustomer(ObjectId id, String key, String value);

    @Query(value="{ '_id': ?0 }", fields="{ 'addData.?1': 1}")
    List<SchemalessCustomer> testGetter(ObjectId id, String key);
    
    @Query("{ '_id': ?0 }")
    @Update("{ $unset: {'additionalData.?1': 1 }}")
    void removeAdditionalDataField(ObjectId id, String key);
}
