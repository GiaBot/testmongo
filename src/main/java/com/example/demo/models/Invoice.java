package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoices")
public class Invoice {
    
    @Id
    private String id;
    private String invNr;
    private String orderNr;
    private String customerId;
    private String orderId;

    public Invoice(String invNr, String orderNr, String customerId, String orderId) {
        this.invNr = invNr;
        this.orderNr = orderNr;
        this.customerId = customerId;
        this.orderId = orderId;
    }

    public Invoice() {

    }

    public void setcustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getcustomerId() {
        return customerId;
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

    public void setorderNr(String orderNr) {
        this.orderNr = orderNr;
    }

    public String getorderIdNr() {
        return orderNr;
    }

    public void setorderIds(String orderId) {
        this.orderId = orderId;
    }

    public String getorderIds() {
        return orderId;
    }

}
