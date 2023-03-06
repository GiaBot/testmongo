package com.example.demo.models;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("schemalessOrder")
public class SchemalessOrders {
    
    @Id
    private ObjectId id;
    private int orderNr;
    private ObjectId customerId;
    private Map<String, Object> modells;
    
    public SchemalessOrders(ObjectId id, int orderNr, ObjectId customerId, Map<String, Object> modells) {
        this.id = id;
        this.orderNr = orderNr;
        this.customerId = customerId;
        this.modells = modells;
    }

    public SchemalessOrders() {
    
    }

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public int getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(int orderNr) {
        this.orderNr = orderNr;
    }
    public ObjectId getCustomerId() {
        return customerId;
    }
    public void setCustomerId(ObjectId customerId) {
        this.customerId = customerId;
    }
    public Map<String, Object> getModells() {
        return modells;
    }
    public void setModells(Map<String, Object> modells) {
        this.modells = modells;
    }
    
}
