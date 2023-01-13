package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoices")
public class Invoice {
    
    @Id
    private String id;
    private int invNr;
    private int orderNr;
    private String customerId;
    private String orderId;

    public Invoice(int invNr, int orderNr, String customerId, String orderId) {
        this.invNr = invNr;
        this.orderNr = orderNr;
        this.customerId = customerId;
        this.orderId = orderId;
    }

    public Invoice() {

    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setInvNr(int invNr) {
        this.invNr = invNr;
    }

    public int getInvNr() {
        return invNr;
    }

    public void setOrderNr(int orderNr) {
        this.orderNr = orderNr;
    }

    public int getOrderIdNr() {
        return orderNr;
    }

    public void setOrderIds(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderIds() {
        return orderId;
    }

}
