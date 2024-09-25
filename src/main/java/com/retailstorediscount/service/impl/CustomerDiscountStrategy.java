package com.retailstorediscount.service.impl;

import java.time.LocalDate;
import java.time.Period;

import com.retailstorediscount.model.Bill;
import com.retailstorediscount.model.Item;
import com.retailstorediscount.service.DiscountStrategy;

public class CustomerDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(Bill bill) {
        if (Period.between(bill.getUser().getCustomerSince(), LocalDate.now()).getYears() > 2) {
            return bill.getItems().stream()
                    .filter(item -> !item.isGrocery())
                    .mapToDouble(Item::getPrice)
                    .sum() * 0.05;
        }
        return 0;
    }
}
