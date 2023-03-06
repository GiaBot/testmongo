package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.SchemalessCustomerRepository;
import com.example.demo.models.SchemalessCustomer;

@RestController
@RequestMapping("/api")
public class SchemalessCustomerController {

    private final SchemalessCustomerRepository repository;

    public SchemalessCustomerController(SchemalessCustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("schemaCustomerId/{id}")
    public List<SchemalessCustomer> findSchemalessCustomerById(ObjectId id) {
        return repository.findSchemalessCustomerById(id);
    }

    @GetMapping("testGetter/{id}")
    public List<SchemalessCustomer> testGetter(@PathVariable ObjectId id, @RequestParam String key) {
        return repository.testGetter(id, key);
    }

    @PostMapping("newCustomer")
    public void newCustomer(@RequestBody SchemalessCustomer customer) {
        repository.save(customer);
    }

    @PutMapping("updateCustomer/{id}")
    public void updateCustomer(@PathVariable ObjectId id, @RequestParam String key, @RequestParam String value) {
        repository.findAndUpdateSchemalessCustomer(id, key, value);
    }
}
