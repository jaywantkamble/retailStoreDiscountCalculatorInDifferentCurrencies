package com.retailstorediscount.service.impl;

import com.retailstorediscount.model.UserType;
import com.retailstorediscount.service.DiscountStrategy;

public class DiscountStrategyFactory {
    public static DiscountStrategy getDiscountStrategy(UserType userType) {
        switch (userType) {
            case EMPLOYEE:
                return new EmployeeDiscountStrategy();
            case AFFILIATE:
                return new AffiliateDiscountStrategy();
            case CUSTOMER:
                return new CustomerDiscountStrategy();
            default:
                return bill -> 0;
        }
    }
}
