package com.retailstorediscount;

import com.retailstorediscount.controller.DiscountController;
import com.retailstorediscount.model.Bill;
import com.retailstorediscount.model.Item;
import com.retailstorediscount.model.User;
import com.retailstorediscount.model.UserType;
import com.retailstorediscount.service.CurrencyExchangeService;
import com.retailstorediscount.service.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class DiscountControllerTest {

    @InjectMocks
    private DiscountController discountController;

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @Mock
    private DiscountService discountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateNetPayableAmount() {
        // Arrange
        User user = new User("John Doe", UserType.OTHER, LocalDate.of(2020, 1, 15));
        Item item1 = new Item("Laptop", 999.99, false);
        Item item2 = new Item("Mouse", 25.50, false);
        Bill bill = new Bill(user, Arrays.asList(item1, item2));
        bill.setOriginalCurrency("USD");
        bill.setTargetCurrency("EUR");

        double exchangeRate = 0.85; // Example exchange rate
        double netPayableAmount = 1050.99; // Example net payable amount

        // Mock the service calls
        when(currencyExchangeService.getExchangeRate("USD", "EUR")).thenReturn(exchangeRate);
        when(discountService.calculateNetPayableAmount(bill)).thenReturn(netPayableAmount);

        // Act
        ResponseEntity<Double> response = discountController.calculateNetPayableAmount(bill);

        // Assert
        assertEquals(ResponseEntity.ok(netPayableAmount * exchangeRate), response);
    }

    @Test
    public void testExchangeRateNotAvailable() {
        // Arrange
        User user = new User("Jane Doe", UserType.OTHER, LocalDate.of(2021, 5, 20));
        Item item1 = new Item("Book", 15.00, false);
        Item item2 = new Item("Veggies", 15.00, true);
        Bill bill = new Bill(user, Arrays.asList(item1,item2));
        bill.setOriginalCurrency("USD");
        bill.setTargetCurrency("GBP");

        // Mock the service calls
        when(currencyExchangeService.getExchangeRate("USD", "GBP")).thenThrow(new RuntimeException("Exchange rate not available"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> discountController.calculateNetPayableAmount(bill));
    }

    @Test
    public void testDiscountCalculationFails() {
        // Arrange
        User user = new User("Jim Bean", UserType.AFFILIATE, LocalDate.of(2019, 3, 12));
        Item item1 = new Item("Coffee Maker", 49.99, false);
        Item item2 = new Item("Banana", 49.99, false);
        Bill bill = new Bill(user, Arrays.asList(item1,item2));
        bill.setOriginalCurrency("CAD");
        bill.setTargetCurrency("USD");

        double exchangeRate = 0.78; // Example exchange rate

        // Mock the service calls
        when(currencyExchangeService.getExchangeRate("CAD", "USD")).thenReturn(exchangeRate);
        when(discountService.calculateNetPayableAmount(bill)).thenThrow(new RuntimeException("Discount calculation failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> discountController.calculateNetPayableAmount(bill));
    }

    @Test
    public void testEmptyBill() {
        // Arrange
        User user = new User("Alice", UserType.OTHER, LocalDate.of(2022, 6, 30));
        Bill bill = new Bill(user, Arrays.asList());
        bill.setOriginalCurrency("USD");
        bill.setTargetCurrency("EUR");

        double exchangeRate = 0.85; // Example exchange rate

        // Mock the service calls
        when(currencyExchangeService.getExchangeRate("USD", "EUR")).thenReturn(exchangeRate);
        when(discountService.calculateNetPayableAmount(bill)).thenReturn(0.0);

        // Act
        ResponseEntity<Double> response = discountController.calculateNetPayableAmount(bill);

        // Assert
        assertEquals(ResponseEntity.ok(0.0), response);
    }

    @Test
    public void testBillWithNoItems() {
        // Arrange
        User user = new User("Bob", UserType.AFFILIATE, LocalDate.of(2018, 9, 5));
        Bill bill = new Bill(user, Arrays.asList());
        bill.setOriginalCurrency("AUD");
        bill.setTargetCurrency("NZD");

        double exchangeRate = 1.10; // Example exchange rate

        // Mock the service calls
        when(currencyExchangeService.getExchangeRate("AUD", "NZD")).thenReturn(exchangeRate);
        when(discountService.calculateNetPayableAmount(bill)).thenReturn(0.0);

        // Act
        ResponseEntity<Double> response = discountController.calculateNetPayableAmount(bill);

        // Assert
        assertEquals(ResponseEntity.ok(0.0), response);
    }

    @Test
    public void testBillWithOneItem() {
        // Arrange
        User user = new User("Charlie", UserType.OTHER, LocalDate.of(2023, 7, 20));
        Item item1 = new Item("Headphones", 199.99, false);
        Bill bill = new Bill(user, Arrays.asList(item1));
        bill.setOriginalCurrency("EUR");
        bill.setTargetCurrency("USD");

        double exchangeRate = 1.05; // Example exchange rate
        double netPayableAmount = 199.99; // Example net payable amount

        // Mock the service calls
        when(currencyExchangeService.getExchangeRate("EUR", "USD")).thenReturn(exchangeRate);
        when(discountService.calculateNetPayableAmount(bill)).thenReturn(netPayableAmount);

        // Act
        ResponseEntity<Double> response = discountController.calculateNetPayableAmount(bill);

        // Assert
        assertEquals(ResponseEntity.ok(netPayableAmount * exchangeRate), response);
    }
}
