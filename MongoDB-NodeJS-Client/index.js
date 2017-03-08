var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');

var mongodbHost = '127.0.0.1';
var mongodbPort = '27017';
var mongodbDatabase = 'world';

// connect string for mongodb server running locally, connecting to a database called test
var url = 'mongodb://'+mongodbHost+':'+mongodbPort + '/' + mongodbDatabase;

// query: https://mongodb.github.io/node-mongodb-native/markdown-docs/queries.html

MongoClient.connect(url, function(err, db) {
   assert.equal(null, err);
   console.log("Connected correctly to server.");
//var cursor = collection.find({});
 
    db.collection('countries').find().toArray(function(err, results){
    console.log("Country One " +JSON.stringify(results[0])); 
    console.log("Name of Country Two " +results[1].name);

    // use cursor to get the country count 
    var cursor = db.collection('countries').find({"continent":"Asia"}, {"sort": "name"});
    cursor.count(function(err, count){
      console.log("Country count in Asia: "+count);
    });

    cursor.each(function(err, country){
       if (err) {
          console.log(err);
       } else
          console.log(country.name);
    }) 
    db.close();
    console.log("Connection to database is closed.");
  }); //find()

}) //connect()