package com.example.demo.models;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Orders {
    
    @Id
    private String id;
    private String amountModells;
    private String order;
    private ArrayList<String> modells;

    public Orders(String amountModells, String order, ArrayList<String> modells) {
        this.amountModells = amountModells;
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

    public ArrayList<String> getModells() {
        return modells;
    }

    public void setModells(ArrayList<String> modells) {
        this.modells = modells;
    }

    public String getAmountModells() {
        return amountModells;
    }

    public void setAmountModells(String amountModells) {
        this.amountModells = amountModells;
    }

}
