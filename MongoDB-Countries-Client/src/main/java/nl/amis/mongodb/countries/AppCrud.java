package nl.amis.mongodb.countries;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.Block;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;

public class AppCrud {

        static Block<Document> printBlock = new Block<Document>() {
                @Override
                public void apply(final Document document) {
                        System.out.println(document.toJson());
                }
        };

        public static void main(String[] args) throws UnknownHostException {
                String mongodbHost = "127.0.0.1";
                Integer mongodbPort = 27017;
                String mongodbDatabase = "world";

                MongoClient mongoClient = new MongoClient(mongodbHost, mongodbPort);
                try {
                        System.out.println("Connected to server; now hook into database " + mongodbDatabase);
                        MongoDatabase db = mongoClient.getDatabase(mongodbDatabase);
                        System.out.println("Connected to database " + mongodbDatabase + " successfully");
                        String nameOfCollection = "myDocs";
                        MongoCollection<Document> coll = db.getCollection(nameOfCollection);
                        System.out.println("Drop collection");
                        coll.drop();
                        System.out.println("Start inserting documents");

                        Document doc1 = new Document("continent", "Europe")
                                        .append("nrs", Arrays.asList(new Document("name", "Belgium"),
                                                        new Document("name", "Luxemburg")))
                                        .append("someVariable", 19123)
                                        .append("andmore", "kl;jdsfakhfsdahjfsdasjbsdahjbsdahgvsahjkZl;po");
                        Document doc2 = new Document("continent", "Asia").append("name", "Johnny")
                                        .append("nrs", Arrays.asList(new Document("name", "China"),
                                                        new Document("name", "India"), new Document("name", "Buthan")))
                                        .append("tree", new Document("branch",
                                                        new Document("twig", new Document("leaf", 1))));
                        List<Document> documents = new ArrayList<Document>();
                        documents.add(doc1);
                        documents.add(doc2);

                        coll.insertMany(documents);
                        System.out.println(">> Documents created into collection");
                        coll.find().forEach(printBlock);
                        // update 
                        coll.updateOne(Filters.eq("tree.branch.twig.leaf", 1),
                                        Updates.combine(Updates.set("name", "Hank"), Updates.set("city", "Nieuwegein"),
                                                        Updates.set("location.province", "Utrecht"),
                                                        Updates.set("location.country", "The Netherlands"),
                                                        Updates.set("tree.stemp", 5),
                                                        Updates.currentDate("lastModified")));
                        System.out.println(">> Updated one document in collection " + nameOfCollection);
                        coll.find().forEach(printBlock);

                        DeleteResult dr = coll.deleteMany(Filters.exists("_id"));
                        System.out.println(">> Deleted " + dr.getDeletedCount() + " documents from collection "
                                        + nameOfCollection);
                        db.runCommand(new Document("drop", nameOfCollection));
                        System.out.println(">> Dropped  collection " + nameOfCollection);

                } finally {
                        mongoClient.close();
                }
        }
}
