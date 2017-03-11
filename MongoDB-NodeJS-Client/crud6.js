var MongoClient = require('mongodb').MongoClient;
var assert = require('assert'),   co = require('co');

var mongodbHost = '127.0.0.1';
var mongodbPort = '27017';
var mongodbDatabase = 'world';

  // http://mongodb.github.io/node-mongodb-native/2.0/reference/ecmascript6/crud/ 

// connect string for mongodb server running locally, connecting to a database called test
var url = 'mongodb://'+mongodbHost+':'+mongodbPort + '/' + mongodbDatabase;

co(function*() {
  // Use connect method to connect to the Server
  var db = yield MongoClient.connect(url);

  console.log("Connected correctly to server");
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
   var nameOfCollection = "myDocs";
   var result = yield db.collection(nameOfCollection).insertMany([doc,doc2]);
   assert.equal(2, result.insertedCount);
   console.log(">> "+result.insertedCount+" documents created into collection "+nameOfCollection);
   var cursor = db.collection(nameOfCollection).find();
   while (yield cursor.hasNext()){
       var doc  = yield cursor.next();
       console.log("Document: " +JSON.stringify(doc));
    }// while cursor
    // update
    // Update selected documents - locate document based on filter, update existing and/or add new attributes 
    result = yield db.collection(nameOfCollection).updateOne({"tree.branch.twig.leaf":1}, {$set: {name: "Hank", city:"Nieuwegein", "location.province":"Utrecht", "location.country":"The Netherlands", "tree.stem":5}});
    assert.equal(1, result.matchedCount);
    assert.equal(1, result.modifiedCount);
    console.log(">> Updated "+result.modifiedCount+" document(s) in collection "+nameOfCollection );
    cursor = db.collection(nameOfCollection).find();
    while (yield cursor.hasNext()){
       var doc  = yield cursor.next();
       console.log("Document: " +JSON.stringify(doc));
    }// while cursor

    result = yield db.collection(nameOfCollection).deleteMany({});
    console.log(">> Deleted "+ result.deletedCount+" documents from collection "+nameOfCollection);
    // drop the collection 
    yield db.command({drop:nameOfCollection});
    console.log(">> Dropped collection "+nameOfCollection);
    // Close the connection
    db.close();
    console.log("Connection to database is closed.");            
}).catch(function(err) {
  console.log(err.stack);
});