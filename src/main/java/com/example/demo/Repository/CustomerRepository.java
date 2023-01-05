package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.models.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
