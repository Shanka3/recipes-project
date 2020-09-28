package com.example.foodhouse.model;

public class FoodData {

    private String itemName;
    private String itemDescription;
    private String itemMethod;
    private String itemImage;
    private String postKey;

    public FoodData() {
    }

    public FoodData(String itemName, String itemDescription, String itemMethod, String itemImage, String postKey) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemMethod = itemMethod;
        this.itemImage = itemImage;
        this.postKey = postKey;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }
    public String getItemMethod() {
        return itemMethod;
    }
    public String getItemImage() {
        return itemImage;
    }
    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }
}
