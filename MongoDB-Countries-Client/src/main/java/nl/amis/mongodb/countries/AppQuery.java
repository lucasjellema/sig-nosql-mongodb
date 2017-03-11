package nl.amis.mongodb.countries;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.BsonArray;
import org.bson.BsonString;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Filters;
import com.mongodb.Block;

//for aggregation framework
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Projections;

public class AppQuery {
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

                        // get all documents from the countries collection 
                        MongoCollection<Document> countries = db.getCollection("countries");
                        System.out.println("Got collection countries  successfully");
                        System.out.println("Number of countries: " + countries.count());
                        System.out.println(
                                        "Retrieve all countries in Asia order alphabeticall by name  and display full country document");

                        countries.find(Filters.eq("continent", "Asia")).sort(Sorts.ascending("name"))
                                        .forEach(new Block<Document>() {
                                                @Override
                                                public void apply(final Document document) {
                                                        System.out.println(document.toJson());
                                                }
                                        });
                        System.out.println("All countries - name and continent");

                        for (Document doc : countries.find()) {
                                // System.out.println(doc.toJson());
                                System.out.println(doc.get("name") + " (" + doc.get("continent") + ")");

                        }
                        /*
                        var aggquery = [  {$sort: {area : -1}}
                                           ,  {$group:{ _id: '$continent'
                              , largestCountry : {$first: "$name"}
                              }
                                              }
                             ];
                        */
                        Block<Document> printBlock = new Block<Document>() {
                                @Override
                                public void apply(final Document document) {
                                        System.out.println("Country: " + document.toJson());
                                }
                        };
                        System.out.println("List largest country in each continent");

                        countries.aggregate(Arrays.asList(Aggregates.sort(Sorts.descending("area")),
                                        Aggregates.group("$continent", Accumulators.first("Largest Country", "$name"))))
                                        .forEach(printBlock);

/*
db.countries.aggregate(
[ {$match: {continent:"Oceania"}}
, {$project:
  { name :{$toUpper:"$name"}
  , populationGrowthRate : {$subtract: ["$birthrate","$deathrate"]}
  , populationDensity : {$trunc :{$divide: ["$population","$area"]}}
  }
}
])
*/
                        System.out.println("List name in uppercase,sorted by size, delta between birthdate and deathrate, population density for countries in Oceania");

BsonArray subArgs=new BsonArray();
            subArgs.add(new BsonString("$birthrate"));
            subArgs.add(new BsonString("$deathrate"));


BsonArray divArgs=new BsonArray();
            divArgs.add(new BsonString("$population"));
            divArgs.add(new BsonString("$area"));
                        countries.aggregate(Arrays.asList(
                                        Aggregates.match(Filters.eq("continent", "Oceania")),
                                        Aggregates.sort(Sorts.descending("area")),
                                        Aggregates.project( Projections.fields(
                                                Projections.excludeId(),
                                                Projections.include("continent"),
                                                Projections.computed("name",Projections.computed("$toUpper",  "$name")),
                                                Projections.computed("populationGrowthRate",Projections.computed("$subtract",  subArgs)),
                                                Projections.computed("populationDensity", Projections.computed("$trunc", Projections.computed("$divide",  divArgs)))
                                                                              )
                                                          )
                                                       )
                                        )
                                        .forEach(printBlock);

                } finally {
                        mongoClient.close();
                }
        }
}
