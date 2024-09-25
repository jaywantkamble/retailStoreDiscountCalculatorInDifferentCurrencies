package com.retailstorediscount;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.retailstorediscount.exception.CurrencyExchangeException;
import com.retailstorediscount.exception.GlobalExceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleCurrencyExchangeException() {
        // Arrange
        String errorMessage = "Currency exchange rate not found";
        CurrencyExchangeException exception = new CurrencyExchangeException(errorMessage);

        // Act
        ResponseEntity<String> response = globalExceptionHandler.handleCurrencyExchangeException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }
    
    @Test
    public void testConstructorWithMessageAndCause() {
        // Arrange
        String errorMessage = "Currency exchange rate not found";
        Throwable cause = new IllegalArgumentException("Invalid argument");
        
        // Act
        CurrencyExchangeException exception = new CurrencyExchangeException(errorMessage, cause);
        
        // Assert
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
