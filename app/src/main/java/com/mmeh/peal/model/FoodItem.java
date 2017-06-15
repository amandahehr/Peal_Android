package com.mmeh.peal.model;

public class FoodItem {

    private String itemId;
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private String itemNDB; // primary key on https://ndb.nal.usda.gov/ndb/
    private String itemMeasure;
    // TODO: check if there is any other attributes for this class. !ATTENTION! Whenever creating new attributes the constructors and the toString() method must be updated/analysed.


    public FoodItem() {
        // TODO: complete this constructor.

    }

    public FoodItem(String itemId, String itemName, String itemDescription, String itemCategory, String itemNDB, String itemMeasure) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemNDB = itemNDB;
        this.itemMeasure = itemMeasure;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemNDB() {
        return itemNDB;
    }

    public void setItemNDB(String itemNDB) {
        this.itemNDB = itemNDB;
    }

    public String getItemMeasure() {
        return itemMeasure;
    }

    public void setItemMeasure(String itemMeasure) {
        this.itemMeasure = itemMeasure;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemNDB='" + itemNDB + '\'' +
                ", itemMeasure='" + itemMeasure + '\'' +
                '}';
    }

}
