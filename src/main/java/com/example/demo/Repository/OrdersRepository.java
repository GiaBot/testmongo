package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.models.Orders;

public interface OrdersRepository extends MongoRepository<Orders, String> {
    
}
