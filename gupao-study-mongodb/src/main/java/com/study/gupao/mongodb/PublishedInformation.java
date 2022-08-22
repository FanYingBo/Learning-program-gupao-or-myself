package com.study.gupao.mongodb;

import java.math.BigDecimal;

public class PublishedInformation {

    private String editorInCharge;
    private String printing;
    private String publisher;
    private String publishDate;
    private BigDecimal price;

    public String getEditorInCharge() {
        return editorInCharge;
    }

    public void setEditorInCharge(String editorInCharge) {
        this.editorInCharge = editorInCharge;
    }

    public String getPrinting() {
        return printing;
    }

    public void setPrinting(String printing) {
        this.printing = printing;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
