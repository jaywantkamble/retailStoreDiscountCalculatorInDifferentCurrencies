package com.retailstorediscount.service;

import com.retailstorediscount.model.Bill;

public interface DiscountStrategy {
    double applyDiscount(Bill bill);
}
