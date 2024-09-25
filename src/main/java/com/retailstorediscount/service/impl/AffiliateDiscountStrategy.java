package com.retailstorediscount.service.impl;

import com.retailstorediscount.model.Bill;
import com.retailstorediscount.model.Item;
import com.retailstorediscount.service.DiscountStrategy;

public class AffiliateDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(Bill bill) {
        return bill.getItems().stream()
                .filter(item -> !item.isGrocery())
                .mapToDouble(Item::getPrice)
                .sum() * 0.10;
    }
}
