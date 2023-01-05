package com.example.demo.models;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Orders {
    
    @Id
    private String id;
    private String nrModells;
    private String order;
    private ArrayList<Model> modells;

    public Orders(String nrModells, String order, ArrayList<Model> modells) {
        this.nrModells = nrModells;
        this.order = order;
        this.modells = modells;
    }

    public Orders() {

    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Model> getModells() {
        return modells;
    }

    public void setModells(ArrayList<Model> modells) {
        this.modells = modells;
    }

    public String getNrModells() {
        return nrModells;
    }

    public void setNrModells(String nrModells) {
        this.nrModells = nrModells;
    }

}
