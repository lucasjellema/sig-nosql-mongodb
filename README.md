# sig-nosql-mongodb
Sources for the MongoDB Handson SIG - 14th March 2017 at AMIS, Nieuwegein, The Netherlands



To run these applications, go through the following steps:

1. git clone https://github.com/lucasjellema/sig-nosql-mongodb

2. Java MongoDB-Countries-Client

(originally created using
mvn archetype:generate -DgroupId=nl.amis.mongodb.countries -DartifactId=MongoDB-Countries-Client -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
)

* cd MongoDB-Countries-Client
* mvn package
* mvn install dependency:copy-dependencies
* java -cp target/MongoDB-Countries-Client-1.0-SNAPSHOT.jar;target/dependency/* nl.amis.mongodb.countries.App 

Resources:
- MongoDB Java Driver installation - http://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/installation/
- MongoDB Java Driver quick start - http://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/ 
- tutorial: https://www.tutorialspoint.com/mongodb/mongodb_java.htm




Note: the following prequisites have to be met:
* a running MongoDB database (release 3.4) 
* Java 8 JRE is installed (1.8.x)
* Apache Maven is installed (3.x)
* Node.JS is installed (6.x or higher)
* npm is installed
