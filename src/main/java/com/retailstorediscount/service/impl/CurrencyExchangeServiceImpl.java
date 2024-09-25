package com.retailstorediscount.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.retailstorediscount.dto.ExchangeRateResponse;
import com.retailstorediscount.exception.CurrencyExchangeException;
import com.retailstorediscount.feign.ExchangeRateClient;
import com.retailstorediscount.service.CurrencyExchangeService;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final ExchangeRateClient exchangeRateClient;

    public CurrencyExchangeServiceImpl(ExchangeRateClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    @Cacheable(value = "exchangeRates", key = "#base + '_' + #target")
    public double getExchangeRate(String base, String target) {
        return fetchExchangeRate(base, target);
    }
    
    public double fetchExchangeRate(String baseCurrency, String targetCurrency) {
        ExchangeRateResponse response = exchangeRateClient.getExchangeRates(baseCurrency);
        if ("success".equals(response.getResult())) {
            return response.getConversionRates().get(targetCurrency);
        } else {
            throw new CurrencyExchangeException("Failed to fetch exchange rate");
        }
    }
    
}
