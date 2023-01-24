package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//TODO Optimize schema of this collection

@Document(collection = "modells")
public class Model {

    @Id
    private String id;
    private String name;
    private List<String> size;
    private String season;
    private List<String> color;
    private float price;

    public Model(String name, List<String> size, String season, 
    List<String> color, float price) {
        this.name = name;
        this.size = size;
        this.season = season;
        this.color = color;
        this.price = price;
    }

    public Model() {
        
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeason() {
        return season;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<String> getColor() {
        return color;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

}
