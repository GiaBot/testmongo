package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
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

import com.example.demo.Repository.ModelRepository;
import com.example.demo.models.Model;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@RestController
@RequestMapping("/api")
public class ModelController {
    
    private final ModelRepository repository;
    private MongoClient client = MongoClients.create(MongoClientSettings.builder()
    .applyConnectionString(new ConnectionString("mongodb://mongoadmin:mongoadmin@localhost:27017/"))
    .build());
    private MongoTemplate mongoTemplate = new MongoTemplate(client, "Test");
   
    ModelController(ModelRepository repository) {
        this.repository = repository;
    }

    @PostMapping("addModel")
    public Model addModel(Model model) {
        return repository.save(model);
    }

    @PostMapping("addModels")
    public List<Model> addModels(@RequestBody List<Model> models) {
        return repository.saveAll(models);
    }

    @GetMapping("modelId/{id}")
    public Optional<Model> getModelById(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping("modelIds")
    public List<Model> getModelByIds(@RequestBody List<String> ids) {
        return repository.findModellsByIds(ids);
    }

    @GetMapping("modelAll")
    public List<Model> getAllModels() {
        return repository.findAll();
    }

    @DeleteMapping("deleteModel/{id}")
    public void deleteModelById(@PathVariable String id) {
        repository.deleteById(id);
    }

    @DeleteMapping("deleteAllModels")
    public void deleteAllModels() {
        repository.deleteAll();
    }

    @PutMapping("saveModel")
    public Model saveModel(@RequestBody Model model) {
        return repository.save(model);
    }

    @PutMapping("saveModels")
    public List<Model> saveModels(List<Model> models) {
        return repository.saveAll(models);
    }

    @PutMapping("updatePrices")
    public void updatePrice(@RequestParam float multiplier) {
        AggregationUpdate update = Aggregation.newUpdate()
            .set("price")
            .toValue(ArithmeticOperators.valueOf("price")
            .multiplyBy(multiplier));

        mongoTemplate.update(Model.class)
            .apply(update)
            .all();
    }

    @PutMapping("updatePrice/{id}")
    public void updatePriceOfId(@PathVariable String id, @RequestParam float multiplier) {
        AggregationUpdate update = Aggregation.newUpdate()
            .set("price")
            .toValue(ArithmeticOperators.valueOf("price")
            .multiplyBy(multiplier));
        
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        mongoTemplate.findAndModify(query, update, Model.class);
    }

}