package com.example.demo.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customners")
public class Customer {
    
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private int PLZ;
    private String city;
    private Invoice invoice;

    public Customer(String firstName, String lastName, String address, int PLZ, String city, Invoice invoice) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.PLZ = PLZ;
        this.city = city;
        this.invoice = invoice;
    }

    public Customer() {
        
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPLZ(int PLZ) {
        this.PLZ = PLZ;
    }

    public int getPLZ() {
        return PLZ;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    static class Invoice {

        private int innr;
        private int ordernr;
        private List<String> order;

        public int getInnr() {
            return innr;
        }

        public void setInnr(int innr) {
            this.innr = innr;
        }

        public List<String> getOrder() {
            return order;
        }

        public void setOrder(List<String> order) {
            this.order = order;
        }

        public int getOrdernr() {
            return ordernr;
        }

        public void setOrdernr(int ordernr) {
            this.ordernr = ordernr;
        }

    }

}
