package com.example.akal.shoppyapp;

/**
 * Created by Gursimran on 12-12-2017.
 */

public class RetriveData {
    private String description, title, type;
    Long productID;
    Long quantity;
    Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }



    public RetriveData(String description, String title, String type, Long productID, Long quantity, Long price) {
        this.description = description;
        this.price = price;
        this.title = title;
        this.type = type;
        this.productID = productID;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "RetriveData{" +
                "description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
