package com.example.demo.Repository;

// import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.http.ResponseEntity;

import com.example.demo.models.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    // public List<Customer> findAll();

    // public ResponseEntity<Customer> findByFirstName(String firstName);
    
    // public ResponseEntity<Customer> findByLastName(String lastName);

    // public ResponseEntity<Customer> findByAddress(String Address);

    // public ResponseEntity<Customer> findByPLZ(int PLZ);

    // public ResponseEntity<Customer> findByCity(String city);

    // public Customer save(Customer customer);

    // public Customer update(Customer customer);

    // public List<Customer> update(List<Customer> custumers);

    // public List<Customer> saveAll(List<Customer> customers);

    // public void delete(String id);

    // public void deleteAll();

}