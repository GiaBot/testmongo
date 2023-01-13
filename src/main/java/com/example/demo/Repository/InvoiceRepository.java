package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.models.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
    
    @Query("{ 'orderNr' : ?0 }")
    List<Invoice> getInvByOrderNr(int orderNr);

    @Query("{ 'invNr' : ?0 }")
    List<Invoice> getInvByInvNr(int invNr);

    @Query("{ 'customerId' : ?0 }")
    List<Invoice> getInvByCustomerId(String id);

    @Query("{ 'orderId' : ?0 }")
    List<Invoice> getInvByOrderId(String id);
}
