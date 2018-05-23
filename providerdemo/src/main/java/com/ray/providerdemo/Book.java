package com.ray.providerdemo;

public class Book {
    private int keyId;
    private String name;
    private int pages;
    private String author;
    private double price;

    public Book() {
    }

    public Book( String name, String author, int pages, double price) {
        this.name = name;
        this.pages = pages;
        this.author = author;
        this.price = price;
    }

    public Book(int keyId, String name, int pages, String author, double price) {
        this.keyId = keyId;
        this.name = name;
        this.pages = pages;
        this.author = author;
        this.price = price;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
