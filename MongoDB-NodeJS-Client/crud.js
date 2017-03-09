var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');

var mongodbHost = '127.0.0.1';
var mongodbPort = '27017';
var mongodbDatabase = 'world';

// connect string for mongodb server running locally, connecting to a database called test
var url = 'mongodb://'+mongodbHost+':'+mongodbPort + '/' + mongodbDatabase;

// find and CRUD: http://mongodb.github.io/node-mongodb-native/2.0/tutorials/crud_operations/

MongoClient.connect(url, function(err, db) {
   assert.equal(null, err);
   console.log("Connected correctly to server.");
   // define two documents, any structure you like
      var doc = {
        "continent" : "Europe",
         "nrs" : [ {"name":"Belgium"}, {"name":"Luxemburg"}],
         "someVariable" : 19123,
         "andmore" : "kl;jdsfakhfsdahjfsdasjbsdahjbsdahgvsahjkZl;po"
      };
   var doc2 = {
        "continent" : "Asia",
        "name" : "Johnny",
         "nrs" : [ {"name":"China"}, {"name":"India"}, {"name":"Buthan"}],
        "tree": {"branch": {"twig":{"leaf": 1}}}
      };
   var nameOfCollection = "myDocs"
   db.collection(nameOfCollection).insertMany([doc,doc2], function(err, r) {
      assert.equal(null, err);
      assert.equal(2, r.insertedCount);
      console.log(r.insertedCount+" documents created into collection "+nameOfCollection);
      var cursor = db.collection(nameOfCollection).find();
      cursor.each(function(err,doc){
        if (err) {
          console.log("Error: "+err)
        } else
            // all documents returned, cursor depleted; done
            if (!doc) {
              // remove litter
              db.collection(nameOfCollection).deleteMany({}, function(err, r) {
                 assert.equal(null, err);
                 console.log("Deleted "+ r.deletedCount+" documents from collection "+nameOfCollection);
                 db.close();
                 console.log("Connection to database is closed.");          
              }); //deleteMany
            }
            else {
              console.log("Document: " +JSON.stringify(doc));
            } 
      })//find.each
    });// insertMany



}) //connect()

