package com.example.demo.models;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Orders {
    
    @Id
    private String id;
    private int amountModells;
    private int order;
    private ArrayList<Model> modells;
    private boolean invoiced = false;
    private float totalPrice;

    public Orders(int amountModells, int order, ArrayList<Model> modells, 
                    boolean invoiced, float totalPrice) {
        this.amountModells = amountModells;
        this.order = order;
        this.modells = modells;
        this.invoiced = invoiced;
        this.totalPrice = totalPrice;
    }

    public Orders() {

    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
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

    public int getAmountModells() {
        return amountModells;
    }

    public void setAmountModells(int amountModells) {
        this.amountModells = amountModells;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public boolean getInvoiced() {
        return this.invoiced;
    }

    public void setInvoiced(boolean invoiced) {
        this.invoiced = invoiced;
    }

}
