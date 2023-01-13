package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

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

@RestController
@RequestMapping("/api")
public class InvoiceController {
    
    private final InvoiceRepository repository;

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
    public List<Invoice> getInvoiceByOrderId(@RequestParam String orderId) {
        return repository.getInvByOrderId(orderId);
    }

    @GetMapping("invoiceInvNr")
    public List<Invoice> getInvoiceByInvNr(@RequestParam int invNr) {
        return repository.getInvByInvNr(invNr);
    }

    @GetMapping("invoiceCustomerId/{customerId}")
    public List<Invoice> getInvoiceByCustomer(@PathVariable String customerId) {
        return repository.getInvByCustomerId(customerId);
    }

    @GetMapping("invoiceOrderNr")
    public List<Invoice> getInvoiceByOrderNr(int orderNr) {
        return repository.getInvByOrderNr(orderNr);
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

}
