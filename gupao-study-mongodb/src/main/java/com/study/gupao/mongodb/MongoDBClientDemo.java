package com.study.gupao.mongodb;


import com.mongodb.client.*;

import java.math.BigDecimal;
import java.time.Instant;


/**
 * mongodb CRUD
 *
 */
public class MongoDBClientDemo {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://192.168.0.198:27017");

        MongoDatabase mytest = mongoClient.getDatabase("mytest");
        MongoCollection<Book> testdemo = mytest.getCollection("books",Book.class);
        Book book = new Book();
        book.setAuthor("龚");
        book.setName("Kubernetes权威指南");
        book.setIntro("Kubernetes 是由谷歌开源的容器集群管理系统");
        PublishedInformation publishedInformation = new PublishedInformation();
        publishedInformation.setPrice(BigDecimal.valueOf(238.00d));
        publishedInformation.setPublisher("电子工业出版社");
        publishedInformation.setPrinting("三河市良远印务");
        publishedInformation.setEditorInCharge("张");
        publishedInformation.setPublishDate(Instant.now().toString());
        book.setPublishedInformation(publishedInformation);
        testdemo.insertOne(book);

        mongoClient.close();
    }
}
