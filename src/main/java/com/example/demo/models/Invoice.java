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
    private String paymentMethod;

    public Invoice(int invNr, int orderNr, String customerId, String paymentMethod, String orderId) {
        this.invNr = invNr;
        this.orderNr = orderNr;
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
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

    public int getOrderNr() {
        return orderNr;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

}
