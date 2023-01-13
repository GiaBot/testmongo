package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.models.Orders;

public interface OrdersRepository extends MongoRepository<Orders, String> {
    
    @Query(value="{ '_id' : ?0 }", fields="{ 'totalPrice': 1 }")
    List<Orders> getTotalPriceById(String id);

    @Query("{ 'amountModells': ?0 }")
    List<Orders> getOrderByAmountModells(int amount);

    @Query("{ 'order': ?0 }")
    List<Orders> getOrderByOrderNr(int orderNr);

}
