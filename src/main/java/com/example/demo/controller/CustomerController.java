package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.CustomerRepository;
import com.example.demo.models.Customer;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerRepository repository;
    private MongoClient client = MongoClients.create(MongoClientSettings.builder()
    .applyConnectionString(new ConnectionString("mongodb://mongoadmin:mongoadmin@localhost:27017/"))
    .build());
    private MongoTemplate mongoTemplate = new MongoTemplate(client, "Test");

    CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("addCustomer")
    public Customer addCustomer(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @PostMapping("addCustomers")
    public List<Customer> addCustomers(@RequestBody List<Customer> customers) {
        return repository.saveAll(customers);
    }

    @GetMapping("customerId/{id}")
    public Optional<Customer> getCustomerById(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping("customerFirstName")
    public List<Customer> findByCustomerByName(@RequestParam String firstName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is(firstName));
        List<Customer> customer = mongoTemplate.find(query, Customer.class);
        return customer;
    }

    @GetMapping("customerLastName")
    public List<Customer> findByCustomerByLastName(@RequestParam String lastName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lastName").is(lastName));
        List<Customer> customer = mongoTemplate.find(query, Customer.class);
        return customer;
    }

    @GetMapping("customerCity")
    public List<Customer> getCustomersByCity(@RequestParam String city) {
        Query query = new Query();
        query.addCriteria(Criteria.where("city").is(city));
        List<Customer> customer = mongoTemplate.find(query, Customer.class);
        return customer;
    }

    @GetMapping("customerPlz")
    public List<Customer> getCustomersByPlz(@RequestParam int plz) {
        Query query = new Query();
        query.addCriteria(Criteria.where("plz").is(plz));
        List<Customer> customer = mongoTemplate.find(query, Customer.class);
        return customer;
    }

    @GetMapping("customerAddress")
    public List<Customer> getCustomerByAddress(@RequestParam String address) {
        Query query = new Query();
        query.addCriteria(Criteria.where("address").is(address));
        List<Customer> customer = mongoTemplate.find(query, Customer.class);
        return customer;
    }

    @GetMapping("getAllCustomers")
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @DeleteMapping("deleteCustomer/{id}")
    public void deleteCustomer(@PathVariable String id) {
        repository.deleteById(id);
    }

    @DeleteMapping("deleteAllCustomers")
    public void deleteAllCustomers() {
        repository.deleteAll();
    }

    @PutMapping("customer")
    public Customer customer(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @PutMapping("customers")
    public List<Customer> customers(@RequestBody List<Customer> customers) {
        return repository.saveAll(customers);
    }
}