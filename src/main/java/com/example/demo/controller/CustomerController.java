package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.CustomerRepository;
import com.example.demo.models.Customer;

@RestController
@RequestMapping("/api")
public class CustomerController {
    
    private final CustomerRepository repository;

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

    @GetMapping("getCustomerFirstName")
    public List<Customer> getCustomerByFirstName(@RequestBody String firstName) {
        List<String> customerNames = asList(firstName.split(","));
        return repository.findAllById(customerNames);
    }

    @GetMapping("customerId/{id}")
    public Optional<Customer> getCustomerById(@PathVariable String id) {
        return repository.findById(id);
    }

    // @GetMapping("getCustomerLastName")
    // public List<Customer> getCustomerByLastName(@RequestBody String lastName) {
    //     List<String> customerNames = asList(lastName.split(","));
    //     return repository.findAllById(customerNames);
    // }

    // @GetMapping("getCutomerPLZ")
    // public ResponseEntity<ResponseEntity<Customer>> getCustomerByPLZ(@RequestBody int PLZ) {
    //     ResponseEntity<Customer> customers = repository.findByPLZ(PLZ);
    //     if (customers == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    //     return ResponseEntity.ok(customers);
    // }

    // @GetMapping("getCustomerCity")
    // public List<Customer> getCustomerByCity(@RequestBody String city) {
    //     List<String> cities = asList(city.split(","));
    //     return repository.findAllById(cities);
    // }

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