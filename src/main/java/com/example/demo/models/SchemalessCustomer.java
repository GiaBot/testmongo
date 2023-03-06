package com.example.demo.models;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

@Document("schemalessCustomer")
public class SchemalessCustomer {
    
    @Id
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String address;
    private Map<String, Object> addData;

    public SchemalessCustomer(String firstName, String lastName, String address, Map<String, Object> addData) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.addData = addData;
    }
    
    public SchemalessCustomer() {

    }

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonAnyGetter
    public Map<String, Object> getAddData() {
        return addData;
    }

    @JsonAnySetter
    public void setAddData(Map<String, Object> schemalessData) {
        this.addData = schemalessData;
    }
    
}
