package com.retailstorediscount.service.impl;

import org.springframework.stereotype.Service;

import com.retailstorediscount.model.Bill;
import com.retailstorediscount.service.DiscountService;
import com.retailstorediscount.service.DiscountStrategy;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Override
    public double calculateNetPayableAmount(Bill bill) {
        double totalAmount = bill.getTotalAmount();
        DiscountStrategy discountStrategy = DiscountStrategyFactory.getDiscountStrategy(bill.getUser().getUserType());
        double percentageDiscount = discountStrategy.applyDiscount(bill);
        double flatDiscount = (int) (totalAmount / 100) * 5;
        return totalAmount - percentageDiscount - flatDiscount;
    }
}