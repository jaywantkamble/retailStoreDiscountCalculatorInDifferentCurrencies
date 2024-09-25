package com.retailstorediscount.service;

import com.retailstorediscount.model.Bill;

public interface DiscountService {
    double calculateNetPayableAmount(Bill bill);
}
