package com.study.gupao.mongodb;

public class Book {

    private String name;
    private String author;
    private PublishedInformation publishedInformation;
    private String intro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public PublishedInformation getPublishedInformation() {
        return publishedInformation;
    }

    public void setPublishedInformation(PublishedInformation publishedInformation) {
        this.publishedInformation = publishedInformation;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
