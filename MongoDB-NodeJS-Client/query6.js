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
    // find top 20 countries by  size
    var results = yield db.collection('countries').find({},{"sort": [["area",-1]]}).limit(20).toArray();
    console.log("Country One " +JSON.stringify(results[0])); 
    console.log("Name of Country Four " +results[3].name+ " and size: " +results[3].area);

    // use cursor to get the country count 
    // note: The cursor also implements the Node.js 0.10.x or higher stream interface allowing us to pipe the results to other streams. 
    var cursor = db.collection('countries').find({"continent":"Asia"}, {"sort": "name"});
    var count = yield cursor.count();
    console.log("Country count in Asia: "+count);

    while (yield cursor.hasNext()){
       var country = yield cursor.next();
        console.log(country.name);
    }


    // take any aggregation operation against the countries collection and store it in the aggquery object 
    // for example: the largest country per continent
    var aggquery = [  {$sort: {area : -1}}
                   ,  {$group:{ _id: '$continent'
                              , largestCountry : {$first: "$name"}
                              }
                      }
     ];
     var aggcursor = db.collection('countries').aggregate(aggquery);
  while (yield aggcursor.hasNext()){
       var result = yield aggcursor.next();
          console.log(JSON.stringify(result));
    }
  // Close the connection
  db.close();
}).catch(function(err) {
  console.log(err.stack);
});