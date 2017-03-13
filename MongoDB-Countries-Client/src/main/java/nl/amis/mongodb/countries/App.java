package nl.amis.mongodb.countries;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Filters;
import com.mongodb.Block;

//for aggregation framework
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Projections;

public class App {
        public static void main(String[] args) throws UnknownHostException {
                String mongodbHost = "127.0.0.1";
                Integer mongodbPort = 27017;
                String mongodbDatabase = "world";

                MongoClient mongoClient = new MongoClient(mongodbHost, mongodbPort);
                try {
                        System.out.println("Connected to server; now hook into database " + mongodbDatabase);
                        MongoDatabase db = mongoClient.getDatabase(mongodbDatabase);
                        System.out.println("Connected to database " + mongodbDatabase + " successfully");
                        //find({"continent":"Asia"}, {"sort": "name"});

                } finally {
                        mongoClient.close();
                }
        }
}
