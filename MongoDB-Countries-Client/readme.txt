docs on MongoDB Java Driver: https://docs.mongodb.com/ecosystem/drivers/java/
and: http://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/ 
http://mongodb.github.io/mongo-java-driver/3.4/driver-async 


entry in Maven pom.xml:

  <dependencies>
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongo-java-driver</artifactId>
        <version>3.4.0</version>
    </dependency>

Steps to build, download and run:

cd C:\data\sig-nosql-mongodb\MongoDB-Countries-Client

BUILD:
mvn package

RUN:
java -cp target/MongoDB-Countries-Client-1.0-SNAPSHOT.jar;target/dependency/* nl.amis.mongodb.countries.App

DOWNLOAD DEPENDENCIES:
mvn install dependency:copy-dependencies 

