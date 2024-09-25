package com.retailstorediscount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@EnableFeignClients(basePackages = "com.retailstorediscount.feign")
public class RetailStoreDiscountApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailStoreDiscountApplication.class, args);
	}

}
