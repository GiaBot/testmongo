package com.example.demo.models;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoices")
public class Invoice {
    
    @Id
    private String id;
    private String invNr;
    private String orderNr;
    private ArrayList<Customer> customer;
    private Orders orders;
    // int sum;

    public Invoice(String invNr, String orderNr, ArrayList<Customer> customer, Orders orders) {
        this.invNr = invNr;
        this.orderNr = orderNr;
        this.customer = customer;
        this.orders = orders;
    }

    public Invoice() {

    }

    public void setCustomer(ArrayList<Customer> customer) {
        this.customer = customer;
    }

    public ArrayList<Customer> getCustomer() {
        return customer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setInvNr(String invNr) {
        this.invNr = invNr;
    }

    public String getInvNr() {
        return invNr;
    }

    public void setOrderNr(String orderNr) {
        this.orderNr = orderNr;
    }

    public String getOrderNr() {
        return orderNr;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Orders getOrders() {
        return orders;
    }

    // public int getSum() {
    //     return sum;
    // }

    // public void setSum(int sum) {
    //     this.sum = sum;
    // }
}
