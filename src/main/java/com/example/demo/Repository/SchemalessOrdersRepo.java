package com.example.demo.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.demo.models.SchemalessOrders;

public interface SchemalessOrdersRepo extends MongoRepository<SchemalessOrders, String> {
    
    @Query("{ '_id': ?0 }")
    @Update("[{ $set: { 'totalPrice': {$sum: 'modells.price'}}}]")
    void updateTotalPrice(ObjectId id);

    @Query("{ '_id': ?0 }")
    List<SchemalessOrders> findSchemalessOrderById(ObjectId id);

}
