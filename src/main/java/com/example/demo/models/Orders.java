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
    private ArrayList<String> modells;
    private String paymentMethod;
    private float totalPrice;

    public Orders(int amountModells, int order, ArrayList<String> modells, String paymentMethod, float totalPrice) {
        this.amountModells = amountModells;
        this.order = order;
        this.modells = modells;
        this.paymentMethod = paymentMethod;
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

    public ArrayList<String> getModells() {
        return modells;
    }

    public void setModells(ArrayList<String> modells) {
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
}
