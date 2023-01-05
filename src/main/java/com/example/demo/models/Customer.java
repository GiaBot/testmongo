package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customners")
public class Customer {
    
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String PLZ;
    private String city;

    public Customer(String firstName, String lastName, String address, String PLZ, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.PLZ = PLZ;
        this.city = city;
    }

    public Customer() {
        
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

}
