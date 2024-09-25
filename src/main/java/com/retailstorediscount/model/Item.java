package com.retailstorediscount.model;

public class Item {
    private String name;
    private double price;
    private boolean isGrocery;

    public Item(String name, double price, boolean isGrocery) {
        this.name = name;
        this.price = price;
        this.isGrocery = isGrocery;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isGrocery() {
        return isGrocery;
    }
}
