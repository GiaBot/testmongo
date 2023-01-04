package com.example.demo.models;

import org.springframework.data.annotation.Id;

public class Invoice {
    
    @Id
    String id;
    int invnr;
    int ordernr;
    Customer customer;
    Model orders;
    // int sum;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setInvnr(int invnr) {
        this.invnr = invnr;
    }

    public int getInvnr() {
        return invnr;
    }

    public void setOrdernr(int ordernr) {
        this.ordernr = ordernr;
    }

    public int getOrdernr() {
        return ordernr;
    }

    public void setOrders(Model orders) {
        this.orders = orders;
    }

    public Model getOrders() {
        return orders;
    }

    // public int getSum() {
    //     return sum;
    // }

    // public void setSum(int sum) {
    //     this.sum = sum;
    // }
}
