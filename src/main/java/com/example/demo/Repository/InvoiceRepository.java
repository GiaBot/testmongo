package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.models.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
    
}
