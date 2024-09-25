package com.retailstorediscount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.retailstorediscount.model.Bill;
import com.retailstorediscount.model.Item;
import com.retailstorediscount.model.User;
import com.retailstorediscount.model.UserType;
import com.retailstorediscount.service.DiscountService;
import com.retailstorediscount.service.impl.DiscountServiceImpl;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountServiceTest {

	private DiscountService discountService;

    @BeforeEach
    public void setUp() {
        discountService = new DiscountServiceImpl();
    }

    @Test
    public void testEmployeeDiscount() {
        User user = new User("John",UserType.EMPLOYEE, LocalDate.now());
        List<Item> items = Arrays.asList(
            new Item("Item1", 200, false),
            new Item("Item2", 100, true),
            new Item("Item3", 300, false)
        );
        Bill bill = new Bill(user, items);
        double netAmount = discountService.calculateNetPayableAmount(bill);
        // Total amount: 600, percentage discount: 150 (30% of 500), flat discount: 30 (5*6)
        assertEquals(420, netAmount, 0.01);
    }

    @Test
    public void testAffiliateDiscount() {
        User user = new User("John", UserType.AFFILIATE, LocalDate.now());
        List<Item> items = Arrays.asList(
            new Item("Item1", 150, false),
            new Item("Item2", 150, true),
            new Item("Item3", 300, false)
        );
        Bill bill = new Bill(user, items);
        double netAmount = discountService.calculateNetPayableAmount(bill);
        // Total amount: 600, percentage discount: 45 (10% of 450), flat discount: 30 (5*6)
        assertEquals(525, netAmount, 0.01);
    }

    @Test
    public void testCustomerDiscount() {
        User user = new User("John",UserType.CUSTOMER, LocalDate.now().minusYears(3));
        List<Item> items = Arrays.asList(
            new Item("Item1", 100, false),
            new Item("Item2", 100, true),
            new Item("Item3", 200, false)
        );
        Bill bill = new Bill(user, items);
        double netAmount = discountService.calculateNetPayableAmount(bill);
        // Total amount: 400, percentage discount: 15 (5% of 300), flat discount: 20 (5*4)
        assertEquals(365, netAmount, 0.01);
    }
    
    @Test
    public void testCustomerDiscountForLessThanTwoYearsOldCustomer() {
        User user = new User("John",UserType.CUSTOMER, LocalDate.now().minusYears(1));
        List<Item> items = Arrays.asList(
            new Item("Item1", 100, false),
            new Item("Item2", 100, true),
            new Item("Item3", 200, false)
        );
        Bill bill = new Bill(user, items);
        double netAmount = discountService.calculateNetPayableAmount(bill);
        // Total amount: 400, percentage discount: 0 (0% of 300), flat discount: 20 (5*4)
        assertEquals(380, netAmount, 0.01);
    }

    @Test
    public void testFlatDiscountOnly() {
        User user = new User("John",UserType.OTHER, LocalDate.now());
        List<Item> items = Arrays.asList(
            new Item("Item1", 150, true),
            new Item("Item2", 200, true),
            new Item("Item3", 150, true)
        );
        Bill bill = new Bill(user, items);
        double netAmount = discountService.calculateNetPayableAmount(bill);
        // Total amount: 500, flat discount: 25 (5*5)
        assertEquals(475, netAmount, 0.01);
    }
    
    
    @Test
    public void testOTHERCustomerDiscountWithNonGroceryItems() {
        User user = new User("John",UserType.OTHER, LocalDate.now());
        List<Item> items = Arrays.asList(
            new Item("Item1", 150, false),
            new Item("Item2", 200, false),
            new Item("Item3", 150, false)
        );
        Bill bill = new Bill(user, items);
        double netAmount = discountService.calculateNetPayableAmount(bill);
        // Total amount: 500, flat discount: 25 (5*5)
        assertEquals(475, netAmount, 0.01);
    }
}