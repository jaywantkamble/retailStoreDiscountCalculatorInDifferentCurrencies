package com.retailstorediscount.dto;

import java.util.Map;

public class ExchangeRateResponse {
    private String result;
    private String base;
    private Map<String, Double> conversionRates;

    // Getters and Setters
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(Map<String, Double> conversionRrates) {
        this.conversionRates = conversionRrates;
    }
}
