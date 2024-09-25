package com.retailstorediscount.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailstorediscount.model.Bill;
import com.retailstorediscount.service.CurrencyExchangeService;
import com.retailstorediscount.service.DiscountService;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {
	
	private final CurrencyExchangeService currencyExchangeService;
	private final DiscountService discountService;

    public DiscountController(CurrencyExchangeService currencyExchangeService, DiscountService discountService) {
		super();
		this.currencyExchangeService = currencyExchangeService;
		this.discountService = discountService;
	}
    
    
	@PostMapping("/calculate")
    public ResponseEntity<Double> calculateNetPayableAmount(@RequestBody Bill bill) {
		double exchangeRate = currencyExchangeService.getExchangeRate(bill.getOriginalCurrency(), bill.getTargetCurrency());
        double netPayableAmount = discountService.calculateNetPayableAmount(bill);
        
        double currencyExchangedPayableAmount = netPayableAmount * exchangeRate;
        
        return ResponseEntity.ok(currencyExchangedPayableAmount);
    }
}
