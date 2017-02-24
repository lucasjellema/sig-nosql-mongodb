package nl.amis.mongodb.countries;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {
    public static void main(String[] args) throws UnknownHostException {

//  connect to local server on default port 27017
//        MongoClient mongoClient = new MongoClient();

// use 127.0.0.1 , do not use localhost!! 
// results in time out socket exception
MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );
        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> product = database
                    .getCollection("product");
            System.out.println("Drop collection");
            product.drop();
            System.out.println("Start inserting documents");
            List<Document> documentList = new ArrayList<Document>();
            documentList.add(new Document("product", "Comfi Bunk Bed")
                    .append("finish", "Dark walnut")
                    .append("desc", "Perfect finish")
                    .append("price", 33000)
                    .append("seller",
                            new BasicDBObject().append("name", "Joe Furnirure")
                                    .append("street", "Baker Street")));

            BasicDBList emailIds = new BasicDBList();
            emailIds.add("a@xyz.com");
            emailIds.add("b@xyz.com");
            documentList.add(new Document("product", "Bed Side Table")
                    .append("finish", "Teak")
                    .append("desc", "Nice and Simple Design")
                    .append("price", 3000)
                    .append("seller",
                            new BasicDBObject().append("name", "Joe Furnirure")
                                    .append("street", "Baker Street")
                                    .append("contact", emailIds)));

            documentList.add(new Document("product", "Study Table").append(
                    "price", 10000).append("seller",
                    new BasicDBObject().append("name", "Joe Furnirure")));
            documentList.add(new Document("product", "Coffee Table").append(
                    "price", 10000).append("seller",
                    new BasicDBObject().append("name", "Billy Furnirure")));
            documentList.add(new Document("product", "Study Table").append(
                    "price", 20000).append("seller",
                    new BasicDBObject().append("name", "Sam Furnirure")));
            documentList.add(new Document("product", "Study Table").append(
                    "price", 5000).append("seller",
                    new BasicDBObject().append("name", "Sam Furnirure")));
            documentList.add(new Document("product", "Wooden Chair").append(
                    "price", 500).append("seller",
                    new BasicDBObject().append("name", "Sam Furnirure")));
            product.insertMany(documentList);
            System.out.println("Done inserting documents");


 MongoDatabase db = mongoClient.getDatabase("world");
          System.out.println("Connect to database successfully");
         // get all documents from the countries collection 
         MongoCollection<Document> coll= db.getCollection("countries");
         System.out.println("Got collection countries  successfully");


			System.out.println("Number of countries: "+coll.count());
for (Document cur : coll.find()) {
    System.out.println(cur.toJson());
}

        } finally {
            mongoClient.close();
        }
    }
}
