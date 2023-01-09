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

import com.example.demo.Repository.OrdersRepository;
import com.example.demo.models.Orders;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@RestController
@RequestMapping("/api")
public class OrdersController {
    
    final private OrdersRepository repository;
    private MongoClient client = MongoClients.create(MongoClientSettings.builder()
    .applyConnectionString(new ConnectionString("mongodb://mongoadmin:mongoadmin@localhost:27017/"))
    .build());
    private MongoTemplate mongoTemplate = new MongoTemplate(client, "Test");

    public OrdersController(OrdersRepository repository) {
        this.repository = repository;
    }

    @GetMapping("ordersId/{id}")
    public Optional<Orders> getOrderById(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping("allOrders")
    public List<Orders> getAllOrders() {
        return repository.findAll();
    }

    @GetMapping("nrModOfOrder")
    public List<Orders> getOrdersByNrOfMod(@RequestParam String nr) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nrModells").is(nr));
        List<Orders> orders = mongoTemplate.find(query, Orders.class);
        return orders;
    }

    @GetMapping("orderNr")
    public List<Orders> getOrdersByOrderNr(@RequestParam String nr) {
        Query query = new Query();
        query.addCriteria(Criteria.where("order").is(nr));
        List<Orders> orders = mongoTemplate.find(query, Orders.class);
        return orders;
    }

    @DeleteMapping("deleteOrderId/{id}")
    public void deleteByOrderId(@PathVariable String id) {
        repository.deleteById(id);
    }

    @DeleteMapping("deleteAllOrders")
    public void deleteAllOrders() {
        repository.deleteAll();
    }

    @PostMapping("addOrder")
    public Orders addOrder(@RequestBody Orders orders) {
        return repository.save(orders);
    }

    @PostMapping("addManyOrders")
    public List<Orders> addManyOrders(@RequestBody List<Orders> orders) {
        return repository.saveAll(orders);
    }

    @PutMapping("saveOrder")
    public Orders saveOrder(@RequestBody Orders orders) {
        return repository.save(orders);
    }

    @PutMapping("saveManyOrders")
    public List<Orders> saveManyOrders(@RequestBody List<Orders> orders) {
        return repository.saveAll(orders);
    }

    
}
