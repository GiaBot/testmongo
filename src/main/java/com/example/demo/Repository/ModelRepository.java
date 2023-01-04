package com.example.demo.Repository;

// import java.util.List;
// import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.models.Model;

public interface ModelRepository extends MongoRepository<Model, String> {

    // public List<Model> findAll();

    // public Optional<Model> findById(String id);

    // public List<Model> findByName(String name);

    // public List<Model> findBySeason(String season);

    // public List<Model> findBySize(String size);

    // public List<Model> findByColor(String color);

    // public List<Model> findByPrice(double price);
    
}