package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;


import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.SchemalessOrdersRepo;
import com.example.demo.models.SchemalessOrders;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.MergeOptions;
import com.mongodb.client.model.MergeOptions.WhenMatched;
import com.mongodb.client.model.MergeOptions.WhenNotMatched;

@RestController
@RequestMapping("/api")
public class SchemalessOrdersController {

        final private SchemalessOrdersRepo repo;

        //Connection to the database
        private MongoClient client = MongoClients.create(MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString("mongodb://mongoadmin:mongoadmin@localhost:27017/"))
                        .build());
        private MongoDatabase database = client.getDatabase("Test2");
        private MongoCollection<Document> model = database.getCollection("schemalessModel");
        private MongoCollection<Document> order = database.getCollection("orders");
        private MongoTemplate mongoTemplate = new MongoTemplate(client, "Test2");

        public SchemalessOrdersController(SchemalessOrdersRepo repo) {
                this.repo = repo;
        }

        @GetMapping("schemalessOrderId/{id}")
        public List<SchemalessOrders> findSchemalessOrderById(@PathVariable ObjectId id) {
                return repo.findSchemalessOrderById(id);
        }

        @PutMapping("test")
        public void updateOrderAmount() {
                // 1.59s -> 100.000 Documents, ca 4-5s PC

                Document multiply = new Document("$multiply", Arrays.asList("$Artikel.Anzahl", "$Artikel.Preis"));

                //Nils Datensatz + Query
                //Custom Query build with documents, for every new action/keyword a new Document,
                //new Document("$some", "Action") = { $some: 'Action' } in MongoDB syntax
                List<Document> query = Arrays.asList(new Document("$match", 
                new Document("Artikel.Preis",
                        new Document("$gt", 0))),
                new Document("$project", 
                new Document("_id", "$_id")
                        .append("Summe", 
                new Document("$reduce", 
                new Document("input", "$Artikel")
                                .append("initialValue", 0L)
                                .append("in", 
                new Document("$sum", Arrays.asList("$$value", 
                                        new Document("$multiply", Arrays.asList("$$this.Preis", "$$this.Anzahl")))))))),
                new Document("$merge", 
                new Document("into", "orders")
                        .append("on", "_id")
                        .append("whenMatched", "merge")
                        .append("whenNotMatched", "discard")));

                mongoTemplate.getDb().getCollection("orders")
                        .aggregate(query)
                        .toCollection();

                
                order.aggregate(Arrays.asList(
                                Aggregates.match(Filters.gt("Artikel.Preis", 0)),
                                Aggregates.unwind("$Artikel"),
                                Aggregates.group("$_id",
                                                Accumulators.sum("Summe", multiply)),
                                Aggregates.merge("orders",
                                                new MergeOptions()
                                                        .uniqueIdentifier("_id")
                                                        .whenMatched(WhenMatched.MERGE)
                                                        .whenNotMatched(WhenNotMatched.DISCARD))))
                                .toCollection();

        }
}
