package com.retailstorediscount;

import com.retailstorediscount.dto.ExchangeRateResponse;
import com.retailstorediscount.exception.CurrencyExchangeException;
import com.retailstorediscount.feign.ExchangeRateClient;
import com.retailstorediscount.service.impl.CurrencyExchangeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Map;

class CurrencyExchangeServiceImplTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @InjectMocks
    private CurrencyExchangeServiceImpl currencyExchangeService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getExchangeRate_ShouldReturnExchangeRate_WhenApiResponseIsSuccessful() {
        // Arrange
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        
        // Create an instance of ExchangeRateResponse
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setResult("success");
        exchangeRateResponse.setConversionRates(Map.of("EUR", 0.85));

        // Mock the API response
        when(exchangeRateClient.getExchangeRates(anyString())).thenReturn(exchangeRateResponse);

        // Act
        double exchangeRate = currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency);

        // Assert
        assertEquals(0.85, exchangeRate);
    }

    @Test
    void getExchangeRate_ShouldThrowException_WhenApiResponseFails() {
        // Arrange
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        
        // Create an instance of ExchangeRateResponse with an error
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setResult("error");

        when(exchangeRateClient.getExchangeRates(anyString())).thenReturn(exchangeRateResponse);

        // Act & Assert
        CurrencyExchangeException exception = assertThrows(CurrencyExchangeException.class, () -> {
            currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency);
        });
        assertEquals("Failed to fetch exchange rate", exception.getMessage());
    }

    @Test
    void getExchangeRate_ShouldThrowException_WhenJsonParsingFails() {
        // Arrange
        String baseCurrency = "USD";
        String targetCurrency = "EUR";

        when(exchangeRateClient.getExchangeRates(anyString())).thenThrow(new CurrencyExchangeException("Failed to fetch exchange rate"));

        // Act & Assert
        CurrencyExchangeException exception = assertThrows(CurrencyExchangeException.class, () -> {
            currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency);
        });
        assertEquals("Failed to fetch exchange rate", exception.getMessage());
    }
}
