var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');
var fs = require("fs");

var mongodbHost = '127.0.0.1';
var mongodbPort = '27017';
var mongodbDatabase = 'world';

// connect string for mongodb server running locally, connecting to a database called test
var url = 'mongodb://'+mongodbHost+':'+mongodbPort + '/' + mongodbDatabase;

// find and CRUD: http://mongodb.github.io/node-mongodb-native/2.0/tutorials/crud_operations/
// aggregation: http://mongodb.github.io/node-mongodb-native/2.0/tutorials/aggregation/

MongoClient.connect(url, function(err, db) {
   assert.equal(null, err);
   console.log("Connected correctly to server.");
//var cursor = collection.find({});
    // find top 20 countries by  size
    db.collection('countries').find({},{"sort": [["area",-1]]}).limit(20).toArray(function(err, results){
    console.log("Country One " +JSON.stringify(results[0])); 
    console.log("Name of Country Four " +results[3].name+ " and size: " +results[3].area);

    // use cursor to get the country count 
    // note: The cursor also implements the Node.js 0.10.x or higher stream interface allowing us to pipe the results to other streams. 
    var cursor = db.collection('countries').find({"continent":"Asia"}, {"sort": "name"});
    cursor.count(function(err, count){
      console.log("Country count in Asia: "+count);
    });

    cursor.each(function(err, country){
       if (err) {
          console.log(err);
       } else
          if (country) {
          console.log(country.name);
          }
    })//each cursor
    // take any aggregation operation against the countries collection and store it in the aggquery object 
    // for example: the largest country per continent
    var aggquery = [  {$sort: {area : -1}}
                   ,  {$group:{ _id: '$continent'
                              , largestCountry : {$first: "$name"}
                              }
                      }
     ];
     var aggcursor = db.collection('countries').aggregate(aggquery);
     aggcursor.each(function(err, result){
       if (err) {
          console.log(err);
       } else if (result)
          console.log(JSON.stringify(result));
     })  //each aggcursor


     // using streams
    
    var ccursor = db.collection('countries').find({});
    // the cursor returned from find and aggregate implements a NodeJS (readable) Stream
    // define event handlers on the stream i.e. the cursor
    ccursor.on('data', function(doc) {
      console.log(doc);
    });

    ccursor.once('end', function() {
      db.close();
      console.log("Connection to database is closed.");
    });
  }); //find()

}) //connect()