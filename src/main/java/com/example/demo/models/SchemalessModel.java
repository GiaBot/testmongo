package com.example.demo.models;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

@Document("schemalessModel")
public class SchemalessModel {
    
    @Id
    private ObjectId id;
    private String name;
    private String season;
    private float price;
    private Map<String, String[]> additionalData;
    
    public SchemalessModel(ObjectId id, String name, String season, float price, Map<String, String[]> additionalData) {
        this.id = id;
        this.name = name;
        this.season = season;
        this.price = price;
        this.additionalData = additionalData;
    }

    public SchemalessModel() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @JsonAnyGetter
    public Map<String, String[]> getadditionalData() {
        return additionalData;
    }

    @JsonAnySetter
    public void setadditionalData(Map<String, String[]> additionalData) {
        this.additionalData = additionalData;
    }

}
