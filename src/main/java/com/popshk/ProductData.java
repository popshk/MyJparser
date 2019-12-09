package com.popshk;

public class ProductData {
    private String articleID;
    private String productName;
    private String brand;
    private String price;
    private String color;


    public ProductData(String articleID, String productName, String brand, String price, String color) {
        this.articleID = articleID;
        this.productName = productName;
        this.brand = brand;
        this.price = price;
        this.color = color;
    }

    public String getArticleID() {
        return articleID;
    }


    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "articleID='" + articleID + '\'' +
                ", productName='" + productName + '\'' +
                ", brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
