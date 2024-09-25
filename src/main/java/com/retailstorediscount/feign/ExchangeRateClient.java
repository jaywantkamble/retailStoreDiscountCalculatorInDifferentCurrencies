package com.retailstorediscount.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.retailstorediscount.dto.ExchangeRateResponse;

@FeignClient(name = "exchange-rate-client", url = "${currency.exchange.api.url}")
public interface ExchangeRateClient {

    @GetMapping("/latest/{baseCurrency}")
    ExchangeRateResponse getExchangeRates(@PathVariable String baseCurrency);
}
