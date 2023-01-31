package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;


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

    @GetMapping("modelIds/{id}")
    public List<Orders> getModelByIds(@PathVariable String id) {
        return repository.getModellsByIds(id);
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
    public void updateTotalPriceById(@PathVariable ObjectId id) {

        // AggregationUpdate update = Aggregation.newUpdate()
        //     .set("totalPrice")
        //     .toValue(ArithmeticOperators.valueOf("modells.price")
        //     .multiplyBy("$modells.amount"));

        // mongoTemplate.update(Orders.class)
        //     .apply(update)
        //     .all();
        Document unwindModells = new Document("$unwind", "$modells");
        Document multiply = new Document("$multiply", Arrays.asList("$modells.price", "$modells.amount"));
        Document setPrice = new Document("$set", new Document("modells.price", multiply));

        orders.aggregate(Arrays.asList(
            Aggregates.match(eq("_id", id)),
            unwindModells,
            setPrice,
            merge("orders")
        )).forEach(doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build())));
        // System.out.println(orders.updateMany(eq("_id", id), setPrice).getModifiedCount());

        orders.aggregate(Arrays.asList(
            Aggregates.match(eq("_id", id)),
            Aggregates.unwind("$modells"),
            Aggregates.group("$_id", 
                Accumulators.sum("totalPrice", "$modells.price")),
            Aggregates.merge("orders")
        )).forEach(doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build())));

    }

    public void updateTotalPrice(String id) {
       /**
        * db.orders.aggregate({$match: {"_id": ObjectId("63cf847b55ac536f07642946") ,
        "modells.price": {$gt: 0}}},{$unwind: "$modells"},
        {$group: {"_id": null, "totalPrice": {$sum: "$modells.price"}}}),
        {$merge: {into: "orders", on: ObjectId("63cf847b55ac536f07642946"), 
        whenMatched: "replace", whenNotMatched: "discard"}}

        db.orders.aggregate({$match: {"_id": ObjectId("63cf847b55ac536f07642959")}},
        {$unwind: "$modells"},{$set: 
        {"modells.price": {$multiply: ["$modells.price","$modells.amount"]}}}
        */
        // Bson group = group(new BsonNull(), sum("totalPrice", "$modells.price"));
        // Bson merge = merge("orders");

        // List<Document> test = orders.aggregate(Arrays.asList(match, unwind, group, merge)).into(new ArrayList<>());

        // test.forEach(doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build())));
        


    }

    public void updateOnPrice() {

    }

}
