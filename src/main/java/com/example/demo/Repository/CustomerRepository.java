package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.models.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query("{firstname:'?0'}")
    public Customer findCustomerByName(String firstName);

    @Query("{lastname:'?0'}")
    public List<Customer> findCustomerByLastName(String lastName);

    @Query(value="{city:'?0'}", fields="{'firstname':1, 'lastname':1, 'city':1}")
    public List<Customer> filterCity(String city);

    @Query("{plz:?0}")
    public List<Customer> filterPLZ(int plz);
    
    @Query("{address:'?0'}")
    public List<Customer> filterAddress(String address);

}
