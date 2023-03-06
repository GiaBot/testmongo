package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBList;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class mongoTest {
	static String[] types = { "T-Shirt", "Hemd", "Jeans", "Pullover" };
	static String[] saisons = { "spring", "summer", "winter", "fall" };
	static String[] sizes = { "S", "M", "L", "XL" };
	static String[] colors = { "blue", "red", "green", "black"};
	static String[] patterns = { "plain", "stripes", "dots", "picture" };
	static String[] fits = {"oversized", "slim", "regular", "skinny"};
	static String[] paymentMethods = {"PayPal", "Creditcard", "Bank transfer", "Real-time payment"};
	static String[] keys = {"type", "color", "size", "print", "fit"};
	static Map<String, List<String>> additionalData;
	private final static int MAX_MODELLS = 100000;
	private final static int MAX_ORDERS = 100000;
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
	private static MongoCollection<Document> schemalessModells = database.getCollection("schemalessModel");
	private static MongoCollection<Document> schemalessOrders = database.getCollection("schemalessOrder");
	private static MongoCollection<Document> schemalessCustomer = database.getCollection("schemalessCustomer");

	public static void main(String[] args) {
		// collection.deleteMany(new Document());
		// orders.deleteMany(new Document());
		// collectionCustomer.deleteMany(new Document());
		invoices.deleteMany(new Document());
		// connectMongoDbCustomer();
		// connectMongoDb();
		// updateModellPrices(2);
		// updateSizePrices(2);
		schemalessModells.deleteMany(new Document());
		schemalessOrders.deleteMany(new Document());
		schemalessModells.insertMany(createSchemalessModel());
		createSchemalessOrders();
		schemalessOrders.insertMany(orderList);
		createInvoices();
		invoices.insertMany(invoiceList);
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
			int randomNr = generateRndNr(0, 2);
			Double tempPrice = 0.0;
			int amount = 0;
			List<Document> newList = new ArrayList<>();
			Document doc = new Document();
			// HashMap<String, Integer> modells = new HashMap<>();
			AggregateIterable<Document> modellList = collection.aggregate(Arrays.asList(Aggregates.sample(nr)));
			for (Document document : modellList) {
				amount = generateRndNr(1, 10);
				tempPrice = document.getDouble("price");
				// modellIds.add(document.get("_id").toString());
				// modells.put(document.get("_id").toString(), amount);
				tempPrice *= amount;
				totalPrice += tempPrice;
				List<Document> size = (List<Document>) document.get("size");
				List<Document> color = (List<Document>) document.get("color");
				// document.remove("size");
				document.put("size", size.get(randomNr));
				document.put("color", color.get(randomNr));
				document.append("amount", amount);
				newList.add(document);
			}
			doc.append("amountModells", nr);
			doc.append("order", key);
			doc.append("modells", newList);
			doc.append("invoiced", false);
			orderList.add(doc);
			key++;
			totalPrice = 0;
		}
	}

	public static void createSchemalessOrders() {
		for (int i = 0; i < MAX_ORDERS; i++) {
			int nr = generateRndNr(1, 11);
			Double tempPrice = 0.0;
			int amount = 0;
			List<Document> newList = new ArrayList<>();
			Document doc = new Document();
			Document newDoc = new Document();
			Map<String, Object> modells;
			AggregateIterable<Document> modellList = schemalessModells.aggregate(Arrays.asList(Aggregates.sample(nr)));
			for (Document document : modellList) {
				Document tempDoc = new Document();
				int randomNr = generateRndNr(0, 2);
				amount = generateRndNr(1, 10);
				tempPrice = document.getDouble("price");
				doc = (Document) document.get("additionalData");
				List<Object> size = (List<Object>) doc.get("sizes");
				List<Object> color = (List<Object>) doc.get("colors");
				List<Object> fit = (List<Object>) doc.get("fits");
				List<Object> print = (List<Object>) doc.get("prints");
				List<Object> type = (List<Object>) doc.get("types");
				tempDoc.put("_id", document.get("_id"));
				tempDoc.append("type", type.get(randomNr));
				tempDoc.append("color", color.get(randomNr));
				tempDoc.append("size", size.get(randomNr));
				tempDoc.append("fit", fit.get(randomNr));
				tempDoc.append("print", print.get(randomNr));
				tempDoc.append("price", tempPrice);
				tempDoc.append("amount", amount);
				newList.add(tempDoc);
			}
			newDoc.append("orderNr", key);
			newDoc.append("modells", newList);
			newDoc.append("totalPrice", totalPrice);
			orderList.add(newDoc);
			key++;
			totalPrice = 0;
		}
	}

	public static void createInvoices() {
		for (int i = 0; i < MAX_MODELLS; i++) {
			invoiceList.add(createInvoice());
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
		BasicDBList sizes = new BasicDBList();
		sizes.add(rSize());
		sizes.add(noDuplicateSize(sizes.get(0).toString(), rSize()));
		BasicDBList colors = new BasicDBList();
		colors.add(rColor());
		colors.add(noDuplicateColor(colors.get(0).toString(), rColor()));
		float priceOne = rPrice();
		float priceTwo = rPrice();
		float sum = priceOne + priceTwo;
		int r = generateRndNr(0, 3);
		switch (r) {
			case 0:
				doc.append("size", sizes);
			case 1:
				doc.append("size", sizes)
						.append("color", colors);
			case 2:
				doc.append("size", sizes)
						.append("color", colors)
						.append("pattern", rPattern());
		}
		doc.append("price", sum);
		return doc;
	}

	public static List<Document> createSchemalessModel() {
		List<Document> documents = new ArrayList<>();
		for (int i = 0; i < MAX_MODELLS; i++) {
			additionalData = new HashMap<>();
			List<String> type = new ArrayList<>();
			type.add(rType());
			type.add(rType());
			List<String> colors = new ArrayList<>();
			colors.add(rColor());
			colors.add(rColor());
			List<String> sizes = new ArrayList<>();
			sizes.add(rSize());
			sizes.add(rSize());
			List<String> fits = new ArrayList<>();
			fits.add(rFit());
			fits.add(rFit());
			List<String> prints = new ArrayList<>();
			prints.add(rPattern());
			prints.add(rPattern());
			additionalData.put("types", type);
			additionalData.put("colors", colors);
			additionalData.put("sizes", sizes);
			additionalData.put("fits", fits);
			additionalData.put("prints", prints);
			Document modell = new Document();
			modell.append("name", randomString());
			modell.append("season", rSaison());
			modell.append("price", rPrice());
			modell.append("additionalData", additionalData);
			documents.add(modell);
		}
		return documents;
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
		Document docSchemalessCust = new Document();
		Document docSchemalessOrd = new Document();
		Document doc = new Document();
		String customerId = "";
		String orderId = "";
		// AggregateIterable<Document> customer = collectionCustomer.aggregate(Arrays.asList(Aggregates.sample(1)));
		// AggregateIterable<Document> order = orders.aggregate(Arrays.asList(Aggregates.sample(1)));
		// docCustomer = customer.first();
		// docOrder = order.first();
		// if (docCustomer != null) {
		// 	customerId = docCustomer.get("_id").toString();
		// }
		// if (docOrder != null) {
		// 	docOrder.put("invoiced", true);
		// 	orderId = docOrder.get("_id").toString();
		// }

		//Schemaless
		AggregateIterable<Document> schemalessCust = schemalessCustomer.aggregate(Arrays.asList(Aggregates.sample(1)));
		AggregateIterable<Document> schemalessOrd = schemalessOrders.aggregate(Arrays.asList(Aggregates.sample(1)));
		docSchemalessCust = schemalessCust.first();
		docSchemalessOrd = schemalessOrd.first();
		if (docSchemalessCust != null) {
			customerId = docSchemalessCust.get("_id").toString();
		}
		if (docSchemalessOrd != null) {
			docSchemalessOrd.put("invoiced", true);
			orderId = docSchemalessOrd.get("_id").toString();
		}



		doc.append("invNr", generateRndNr(0, 100000))
				.append("orderNr", orderNr)
				.append("customerId", customerId)
				.append("orderId", orderId)
				.append("paymentMethod", rPaymentMethod());
		orderNr++;
		return doc;
	}

	public static String rName() {
		return randomString();
	}

	public static String rType() {
		String type = types[generateRndNr(0, 4)];
		return type;
	}

	public static String rFit() {
		String fit = fits[generateRndNr(0, 4)];
		return fit;
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
		String color = colors[generateRndNr(0, 4)];
		return color;
	}

	public static String rPattern() {
		String pattern = patterns[generateRndNr(0, 4)];
		return pattern;
	}

	public static float rPrice() {
		float price = generateRndNr(1, 100);
		return price;
	}

	public static String rPaymentMethod() {
		String paymentMethod = paymentMethods[generateRndNr(0, 4)];
		return paymentMethod;
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
