package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import ch.qos.logback.core.model.Model;

@RestController
@RequestMapping("/api")
public class OrdersController {
    
    final private OrdersRepository repository;
    private MongoClient client = MongoClients.create(MongoClientSettings.builder()
    .applyConnectionString(new ConnectionString("mongodb://mongoadmin:mongoadmin@localhost:27017/"))
    .build());
    private MongoDatabase database = client.getDatabase("Test");
    private MongoCollection<Document> orders = database.getCollection("orders");
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
    public List<Orders> getOrdersByNrOfMod(@RequestParam int amount) {
        return repository.getOrderByAmountModells(amount);
    }

    @GetMapping("orderNr")
    public List<Orders> getOrdersByOrderNr(@RequestParam int order) {
        return repository.getOrderByOrderNr(order);
    }

    @GetMapping("totalPrice/{id}")
    public List<Orders> getTotalPriceById(@PathVariable String id) {
        return repository.getTotalPriceById(id);
    }

    @GetMapping("paymentMethod")
    public List<Orders> getOrderByPaymentMethod(@RequestParam String paymentMethod) {
        return repository.getOrderByPaymentMethod(paymentMethod);
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

    @PutMapping("changeAmount/{id}")
    public void changeAmount(@PathVariable String id, @RequestParam int amount, @RequestParam String modellId) {

    }

    @PutMapping("updateTotalPrice/{id}")
    public void updateTotalPriceById(@PathVariable String id) {
        // float totalPrice = orders.get(0).getTotalPrice();
        // orders.get(0).setTotalPrice(totalPrice * amount);
        // repository.save(orders.get(0));

        // AggregationUpdate update = Aggregation.newUpdate()
        //     .set("price")
        //     .toValue(ArithmeticOperators.valueOf("price")
        //     .multiplyBy(amount));

        // mongoTemplate.update(Orders.class)
        //     .apply(update)
        //     .all();
        AggregationUpdate update = Aggregation.newUpdate()
            .set("totalPrice")
            .toValue(ArithmeticOperators.valueOf("modells.price")
            .multiplyBy("modells.amount"));

    }

    public void updateTotalPrice(String id) {
        // String[] keys;
        // Integer[] values;
        // Query query = new Query();
        // query.addCriteria(Criteria.where("_id").is(id));
        // ArrayList<Bson> updateList = new ArrayList<>();
        // Orders order = mongoTemplate.findOne(query, Orders.class);
        // if (order != null) {
        //     values = (Integer[]) modells.values().toArray();
        //     for (Integer integer : values) {
        //         Bson updates = Updates.mul("totalPrice", integer);
        //         updateList.add(updates);
        //     }
        //     models.updateMany(Filters.in("_id", modells.keySet()), updateList);
        // }


        
        AggregationUpdate update = Aggregation.newUpdate()
            .set("totalPrice")
            .toValue(ArithmeticOperators.valueOf("price")
            .multiplyBy("amount"));

        orders.updateMany(new Document(),
                new Document("$set",
                new Document("totalPrice", update)));

    }

    public void updateOnPrice() {

    }

}
