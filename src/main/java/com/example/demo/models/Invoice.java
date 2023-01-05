package com.example.demo.models;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoices")
public class Invoice {
    
    @Id
    String id;
    String invnr;
    String ordernr;
    ArrayList<Customer> customer;
    Orders orders;
    // int sum;

    public Invoice(String invnr, String ordernr, ArrayList<Customer> customer, Orders orders) {
        this.invnr = invnr;
        this.ordernr = ordernr;
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

    public void setInvnr(String invnr) {
        this.invnr = invnr;
    }

    public String getInvnr() {
        return invnr;
    }

    public void setOrdernr(String ordernr) {
        this.ordernr = ordernr;
    }

    public String getOrdernr() {
        return ordernr;
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
