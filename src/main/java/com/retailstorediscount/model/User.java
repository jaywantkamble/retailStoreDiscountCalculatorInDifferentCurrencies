package com.retailstorediscount.model;

import java.time.LocalDate;


public class User {
    private String name;
    private UserType userType;
    private LocalDate customerSince;

    public User(String name, UserType userType, LocalDate customerSince) {
        this.name = name;
        this.userType = userType;
        this.customerSince = customerSince;
    }

    public String getName() {
        return name;
    }

    public UserType getUserType() {
        return userType;
    }

    public LocalDate getCustomerSince() {
        return customerSince;
    }
}
