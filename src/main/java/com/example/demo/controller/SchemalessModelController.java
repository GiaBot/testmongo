package com.example.demo.controller;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.SchemalessModelRepo;
import com.example.demo.models.SchemalessModel;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@RestController
@RequestMapping("/api")
public class SchemalessModelController {

    final private SchemalessModelRepo repo;
    private MongoClient client = MongoClients.create(MongoClientSettings.builder()
    .applyConnectionString(new ConnectionString("mongodb://mongoadmin:mongoadmin@localhost:27017/"))
    .build());
    private MongoDatabase database = client.getDatabase("Test");
    private MongoCollection<Document> model = database.getCollection("schemalessModel");
    private MongoTemplate mongoTemplate = new MongoTemplate(client, "Test");

    public SchemalessModelController(SchemalessModelRepo repo) {
        this.repo = repo;
    }

    @GetMapping("findByName")
    public List<SchemalessModel> findByName(@RequestParam String name) {
        return repo.findByName(name);
    }

    @PostMapping("newSchemalessModel")
    public SchemalessModel addSchemalessModel(@RequestBody SchemalessModel model) {
        return repo.save(model);
    }

    @GetMapping("getAdditionalData/{id}")
    public List<SchemalessModel> getAdditionalData(@PathVariable ObjectId id, @RequestParam String key) {
        return repo.getAdditionalData(id, key);
    }

    @PutMapping("setAdditionalData/{id}")
    public void setAdditionalData(@PathVariable ObjectId id, @RequestParam String key, @RequestBody String[] values) {
        repo.setAdditionalData(id, key, values);
    }

    @PutMapping("addAdditionalData/{id}")
    public void addAdditionalData(@PathVariable ObjectId id, @RequestParam String key, @RequestParam String value) {
        repo.addAdditionalData(id, key, value);
    }

    @PutMapping("removeAdditionalData/{id}")
    public void removeAdditionalData(@PathVariable ObjectId id, @RequestParam String key, @RequestBody String[] values) {
        repo.removeAdditionalData(id, key, values);
    }
    
    @PutMapping("removeADField/{id}")
    public void removeAdditionalDataField(@PathVariable ObjectId id, String key) {
        repo.removeAdditionalDataField(id, key);
    }

    @PutMapping("updateAllPricesNew")
    public void setPrices(@RequestParam double multiplier) {
        repo.setPrices(multiplier);
    }

    @GetMapping("schemalessModelId/{id}")
    public List<SchemalessModel> getSchemalessModellById(@PathVariable ObjectId id) {
        return repo.getModelById(id);
    }

}
