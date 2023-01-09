package com.example.demo.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Orders {
    
    @Id
    private String id;
    private String amountModells;
    private String order;
    private List<Model> modells;
    private float sum;

    public Orders(String amountModells, String order, List<Model> modells, float sum) {
        this.amountModells = amountModells;
        this.order = order;
        this.modells = modells;
        this.sum = sum;
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

    public List<Model> getModells() {
        return modells;
    }

    public void setModells(List<Model> modells) {
        this.modells = modells;
    }

    public String getAmountModells() {
        return amountModells;
    }

    public void setAmountModells(String amountModells) {
        this.amountModells = amountModells;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
