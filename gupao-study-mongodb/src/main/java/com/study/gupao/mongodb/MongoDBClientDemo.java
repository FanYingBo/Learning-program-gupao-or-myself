package com.study.gupao.mongodb;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

/**
 * mongodb CRUD
 *
 */
public class MongoDBClientDemo {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("192.168.8.156",27017);
        MongoDatabase mytest = mongoClient.getDatabase("mytest");
        MongoCollection<Document> testdemo = mytest.getCollection("testdemo");
        Document document = new Document();
        document.append("name","jack").append("age",57).append("addr","sdass");
        testdemo.insertOne(document);
        FindIterable<Document> documents = testdemo.find(BsonDocument.parse("{\"name\":\"jack\"}"));
        documents.forEach((Block<? super Document>) document1 -> System.out.println(document1.get("name")));
        testdemo.deleteMany(new Document().append("name","jack"));
        mongoClient.close();
    }
}
