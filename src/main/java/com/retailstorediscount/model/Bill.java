package com.retailstorediscount.model;

import java.util.List;

public class Bill {
    private User user;
    public void setUser(User user) {
		this.user = user;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	private List<Item> items;
    private String originalCurrency;     // Currency of the bill total
    private String targetCurrency;   // Currency in which bill to be returned

    public Bill(User user, List<Item> items) {
        this.user = user;
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

	public String getOriginalCurrency() {
		return originalCurrency;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}
}