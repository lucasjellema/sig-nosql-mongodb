package nl.amis.mongodb.countries;

import java.net.UnknownHostException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class AppConnect {
    public static void main(String[] args) throws UnknownHostException {
        String mongodbHost = "127.0.0.1";
        Integer mongodbPort = 27017;
        String mongodbDatabase = "world";

        MongoClient mongoClient = new MongoClient(mongodbHost, mongodbPort);
        try {
            System.out.println("Connected to server; now hook into database " + mongodbDatabase);
            MongoDatabase db = mongoClient.getDatabase(mongodbDatabase);
            System.out.println("Connected to database " + mongodbDatabase + " successfully");
        } finally {
            mongoClient.close();
            System.out.println("Closed mongodb connection");
        }
    }
}
