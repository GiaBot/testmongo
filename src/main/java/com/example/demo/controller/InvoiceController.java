package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.InvoiceRepository;
import com.example.demo.models.Invoice;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@RestController
@RequestMapping("/api")
public class InvoiceController {
    
    private final InvoiceRepository repository;
    private MongoClient client = MongoClients.create(MongoClientSettings.builder()
    .applyConnectionString(new ConnectionString("mongodb://mongoadmin:mongoadmin@localhost:27017/"))
    .build());
    private MongoTemplate mongoTemplate = new MongoTemplate(client, "Test");

    InvoiceController(InvoiceRepository repository) {
        this.repository = repository;
    }

    @GetMapping("allInvoices")
    public List<Invoice> getAllInvoices() {
        return repository.findAll();
    }

    @GetMapping("invoiceId/{id}")
    public Optional<Invoice> getInvoiceById(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping("invoiceOrderId")
    public List<Invoice> getInvoiceByOrder(@RequestParam String orderId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("ordernr").is(orderId));
        List<Invoice> invoice = mongoTemplate.find(query, Invoice.class);
        return invoice;
    }

    @GetMapping("invoiceInvNr")
    public List<Invoice> getInvoiceByInvNr(@RequestParam String invNr) {
        Query query = new Query();
        query.addCriteria(Criteria.where("invnr").is(invNr));
        List<Invoice> invoice = mongoTemplate.find(query, Invoice.class);
        return invoice;
    }

    @GetMapping("invoiceCustomerId/{customerId}")
    public List<Invoice> getInvoiceByCustomer(@PathVariable String customerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("customer.id").is(customerId));
        List<Invoice> invoice = mongoTemplate.find(query, Invoice.class);
        return invoice;
    }

    @GetMapping("invoiceOrderId/{modellId}")
    public List<Invoice> getInvoiceByModel(@PathVariable String modellId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("orders.id").is(modellId));
        List<Invoice> invoice = mongoTemplate.find(query, Invoice.class);
        return invoice;
    }

    @DeleteMapping("deleteInvoice/{id}")
    public void deleteInvoideById(@PathVariable String id) {
        repository.deleteById(id);
    }

    @DeleteMapping("deleteAllInvoices")
    public void deleteAllInvoices() {
        repository.deleteAll();
    }

    @PutMapping("saveInvoice")
    public Invoice saveInvoice(@RequestBody Invoice invoice) {
        return repository.save(invoice);
    }

    @PutMapping("saveAllInvoices")
    public List<Invoice> saveAllInvoices(@RequestBody List<Invoice> invoices) {
        return repository.saveAll(invoices);
    }

    @PostMapping("addInvoice")
    public Invoice addInvoice(@RequestBody Invoice invoice) {
        return repository.save(invoice);
    }

    public List<Invoice> addManyInvoices(@RequestBody List<Invoice> invoices) {
        return repository.saveAll(invoices);
    }

    //TO-DO: Sum of the modells in the order
}
