package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class mongoTest {
	static String[] types = { "T-Shirt", "Hemd", "Jeans", "Socken", "Pullover", "Jacke" };
	static String[] saisons = { "spring", "summer", "winter", "fall" };
	static String[] sizes = { "S", "M", "L", "XL" };
	static String[] colors = { "blue", "red", "green", "black", "grey" };
	static String[] patterns = { "plain", "stripes" };
	private final static int MAX_MODELLS = 100;
	private final static int MAX_ORDERS = 100;
	static List<Document> list = new ArrayList<>();
	static List<Document> orderList = new ArrayList<>();
	static List<Document> customerList = new ArrayList<>();
	static List<Document> invoiceList = new ArrayList<>();
	static List<Document> modellsList = new ArrayList<>();
	static Document order;
	private static int key = 0;
	private static int orderNr = 0;
	private static float totalPrice = 0;
	private static MongoClient mongoClient = MongoClients.create("mongodb://mongoadmin:mongoadmin@localhost:27017");
	private static MongoDatabase database = mongoClient.getDatabase("Test");
	private static MongoCollection<Document> collectionCustomer = database.getCollection("customners");
	private static MongoCollection<Document> collection = database.getCollection("modells");
	private static MongoCollection<Document> orders = database.getCollection("orders");
	private static MongoCollection<Document> invoices = database.getCollection("invoices");

	public static void main(String[] args) {
		// collection.deleteMany(new Document());
		// orders.deleteMany(new Document());
		// collectionCustomer.deleteMany(new Document());
		// invoices.deleteMany(new Document());
		// connectMongoDbCustomer();
		// connectMongoDb();
		// updateModellPrices(2);
		updateSizePrices(2);
	}

	public static void connectMongoDb() {
		for (int i = 0; i < MAX_MODELLS; i++) {
			list.add(randomDoc(createDocument()));
		}
		collection.insertMany(list);
		createOrders();
		orders.insertMany(orderList);
		createInvoices();
		invoices.insertMany(invoiceList);

		// for(int i = 0; i < MAX_ORDERS; i++) {
		// AggregateIterable<Document> test =
		// collection.aggregate(Arrays.asList(Aggregates.sample(generateRndNr(1, 11)),
		// Aggregates.out("orders")));
		// }

	}

	public static void connectMongoDbCustomer() {
		for (int i = 0; i < MAX_MODELLS; i++) {
			customerList.add(createCustomers());
		}
		collectionCustomer.insertMany(customerList);
	}

	public void createDocuments() {
		for (int i = 0; i < MAX_MODELLS; i++) {
			list.add(randomDoc(createDocument()));
		}
	}

	public static Document createDocument() {

		Document modell = new Document("name", rName())
				.append("type", rType())
				.append("season", rSaison());
		return modell;
	}

	public static void createOrders() {
		for (int i = 0; i < MAX_ORDERS; i++) {
			int nr = generateRndNr(1, 11);
			List<String> modellIds = new ArrayList<>();
			Document doc = new Document();
			AggregateIterable<Document> modellList = collection.aggregate(Arrays.asList(Aggregates.sample(nr)));
			for (Document document : modellList) {
				totalPrice += document.getDouble("price");
				modellIds.add(document.get("_id").toString());
			}
			doc.append("amountModells", nr);
			doc.append("order", key);
			doc.append("modells", modellIds);
			doc.append("totalPrice", totalPrice);
			orderList.add(doc);
			key++;
			totalPrice = 0;
		}
	}

	public static void createInvoices() {
		for (int i = 0; i < MAX_MODELLS; i++) {
			invoiceList.add(createInvoice());
		}
	}

	//TODO
	public static void updateSizePrices(int multiplier) {
		Bson query = Filters.gt("size.$[]", 0);
		Bson update = Updates.mul("size.$[]",multiplier);
		try {
			collection.updateMany(query, update);
		} catch (Exception e) {
		}
	}

	public static void updateModellPrices(int multiplier) {
		Bson query = Filters.gt("price", 0);
		Bson update = Updates.mul("price",multiplier);
		try {
			collection.updateMany(query, update);
		} catch (Exception e) {
		}
	}

	public static String noDuplicateSize(String one, String two) {
		if (one == two) {
			while (one == two) {
				two = rSize();
			}
		}
		return two;
	}

	public static String noDuplicateColor(String one, String two) {
		if (one == two) {
			while (one == two) {
				two = rColor();
			}
		}
		return two;
	}

	public static Document randomDoc(Document doc) {
		String sizeOne = rSize();
		String sizeTwo = rSize();
		String colorOne = rColor();
		String colorTwo = rColor();
		float priceOne = rPrice();
		float priceTwo = rPrice();
		float sum = priceOne + priceTwo;
		int r = generateRndNr(0, 3);
		switch (r) {
			case 0:
				doc.append("size", new BasicDBObject(sizeOne, priceOne)
						.append(noDuplicateSize(sizeOne, sizeTwo), priceTwo));
			case 1:
				doc.append("size", new BasicDBObject(sizeOne, priceOne)
						.append(noDuplicateSize(sizeOne, sizeTwo), priceTwo))
						.append("color", new BasicDBObject(sizeOne, colorOne)
								.append(sizeTwo, colorTwo));
			case 2:
				doc.append("size", new BasicDBObject(sizeOne, priceOne)
						.append(noDuplicateSize(sizeOne, sizeTwo), priceTwo))
						.append("color", new BasicDBObject(sizeOne, colorOne)
								.append(sizeTwo, colorTwo))
						.append("pattern", rPattern());
		}
		doc.append("price", sum);
		return doc;
	}

	public static Document createCustomers() {
		Document doc = new Document();
		doc.append("firstName", randomString())
				.append("lastName", randomString())
				.append("address", randomString() + " Street")
				.append("PLZ", String.valueOf(generateRndNr(1, 10000)))
				.append("city", randomString());

		return doc;
	}

	public static Document createInvoice() {
		Document docCustomer = new Document();
		Document docOrder = new Document();
		Document doc = new Document();
		String customerId = "";
		String orderId = "";
		AggregateIterable<Document> customer = collectionCustomer.aggregate(Arrays.asList(Aggregates.sample(1)));
		AggregateIterable<Document> order = orders.aggregate(Arrays.asList(Aggregates.sample(1)));
		docCustomer = customer.first();
		docOrder = order.first();
		if (docCustomer != null) {
			customerId = docCustomer.get("_id").toString();
		}
		if (docOrder != null) {
			orderId = docOrder.get("_id").toString();
		}
		doc.append("invNr", generateRndNr(0, 100000))
				.append("orderNr", orderNr)
				.append("customerId", customerId)
				.append("orderId", orderId);
		orderNr++;
		return doc;
	}

	public static String rName() {
		return randomString();
	}

	public static String rType() {
		String type = types[generateRndNr(0, 6)];
		return type;
	}

	public static String rSaison() {
		String saison = saisons[generateRndNr(0, 4)];
		return saison;
	}

	public static String rSize() {
		String size = sizes[generateRndNr(0, 4)];
		return size;
	}

	public static String rColor() {
		String color = colors[generateRndNr(0, 5)];
		return color;
	}

	public static String rPattern() {
		String pattern = patterns[generateRndNr(0, 2)];
		return pattern;
	}

	public static float rPrice() {
		float price = generateRndNr(1, 100);
		return price;
	}

	public static int generateRndNr(int low, int high) {
		Random r = new Random();
		int RANDOM_NR = r.nextInt(high - low) + low;
		return RANDOM_NR;
	}

	public static String randomString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}
}
