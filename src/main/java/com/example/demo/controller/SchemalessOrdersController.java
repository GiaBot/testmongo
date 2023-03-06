package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators;
import org.springframework.data.mongodb.core.aggregation.ScriptOperators.Accumulator;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.SchemalessOrdersRepo;
import com.example.demo.models.SchemalessModel;
import com.example.demo.models.SchemalessOrders;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.MergeOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.MergeOptions.WhenMatched;
import com.mongodb.client.model.MergeOptions.WhenNotMatched;

@RestController
@RequestMapping("/api")
public class SchemalessOrdersController {

        final private SchemalessOrdersRepo repo;
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
                // 1.59s -> 100.000 Documents

                Document multiply = new Document("$multiply", Arrays.asList("$Artikel.Anzahl", "$Artikel.Preis"));

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
                System.out.println(query);

                mongoTemplate.getDb().getCollection("orders")
                        .aggregate(query)
                        .toCollection();

                // order.aggregate(Arrays.asList(
                //                 Aggregates.match(Filters.gt("Artikel.Preis", 0)),
                //                 Aggregates.unwind("$Artikel"),
                //                 Aggregates.group("$_id",
                //                                 Accumulators.sum("Summe", multiply)),
                //                 Aggregates.merge("orders",
                //                                 new MergeOptions()
                //                                         .uniqueIdentifier("_id")
                //                                         .whenMatched(WhenMatched.MERGE)
                //                                         .whenNotMatched(WhenNotMatched.DISCARD))))
                //                 .toCollection();

                // 223ms -> 10.000 Documents
                // ca. 1,60s -> 100.000 Documents
                // List<Document> pipeline = Arrays.asList(new Document("$match",
                //                 new Document("modells.price",
                //                                 new Document("$gt", 0))),
                //                 new Document("$unwind", "$modells"),
                //                 new Document("$group",
                //                                 new Document("_id", "$_id")
                //                                         .append("totalPrice",
                //                                         new Document("$sum",
                //                                         new Document("$multiply", Arrays.asList("$modells.amount", "$modells.price"))))),
                //                 new Document("$merge",
                //                                 new Document("into", "schemalessOrder")
                //                                         .append("whenMatched", "merge")
                //                                         .append("whenNotMatched", "discard")));

                // mongoTemplate.getDb().getCollection("schemalessOrder", SchemalessOrders.class)
                //                 .aggregate(pipeline)
                //                 .toCollection();
        }

        @PutMapping("test2")
        public void updateOrderPrices() {
                // 1m 49s -> 10000 Documents
                // Too long for 100000 Documents
                int counter = 0;
                int counter2 = 0;
                double price = 0;
                double oPrice = 0;
                int amount = 0;
                ObjectId id;
                Object id2 = null;
                FindIterable<Document> orderList;
                Map<ObjectId, Document> testList = new HashMap<>();
                ArrayList<Document> testOrder = new ArrayList<>();
                FindIterable<Document> list = model.find(Filters.gt("price", 0));
                for (Document document : list) {
                        oPrice = document.getDouble("price");
                        id = document.getObjectId("_id");
                        orderList = order.find(Filters.eq("modells._id", id));
                        for (Document document2 : orderList) {
                                counter2++;
                                System.out.println("orderList:" + counter2);
                                List<Document> test = (List<Document>) document2.get("modells");
                                for (int i = 0; i < test.size(); i++) {
                                        if (!test.get(i).getObjectId("_id").equals(id)) {
                                                System.out.println("continue");
                                                continue;
                                        } else {
                                                System.out.println("not continue");
                                                id2 = test.get(i).getObjectId("_id");
                                                price = oPrice;
                                                amount = test.get(i).getInteger("amount");
                                                price *= amount;
                                                test.get(i).put("price", price);
                                                break;
                                        }
                                }
                                document2.put("modells", test);
                                System.out.println(document2.toJson());
                                if(id2 == null){
                                        System.out.println("null");
                                        continue;
                                }
                                else if(testList.containsKey(id)) {
                                        System.out.println(id.toString());
                                        testList.replace(id, document2);
                                } else {
                                        System.out.println("else");
                                        testList.put(id, document2);
                                }
                        }
                        counter++;
                        System.out.println(counter);
                }
                System.out.println(testList.size());
                order.deleteMany(new Document());
                order.insertMany(new ArrayList<>(testList.values()));
                

        }
}
