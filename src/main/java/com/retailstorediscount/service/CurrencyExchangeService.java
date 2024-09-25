package com.retailstorediscount.service;

public interface CurrencyExchangeService {

	double getExchangeRate(String baseCurrency, String targetCurrency);
}
