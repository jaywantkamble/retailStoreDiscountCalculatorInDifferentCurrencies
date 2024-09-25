# retailStoreDiscountCalculatorInDifferentCurrencies

## Overview

This Spring Boot application that integrates with a third-party currency exchange API to retrieve real-time exchange rates. The application should calculate the total payable amount for a bill in a specified currency after applying applicable discounts. The application should expose an API endpoint that allows users to submit a bill in one currency and get the payable amount in another currency.

## Features
Discounts and Currency Conversion Logic:
### Apply discounts as per the following rules:
- If the user is an employee of the store, they get a 30% discount.
- If the user is an affiliate of the store, they get a 10% discount.
- If the user has been a customer for over 2 years, they get a 5% discount.
- For every $100 on the bill, there is a $5 discount.
- The percentage-based discounts do not apply to groceries.
- A user can get only one of the percentage-based discounts on a bill.
- Convert the bill total from the original currency to the target currency using the retrieved exchange rates.
- Calculate the final payable amount in the target currency after applying the applicable discounts.


## Requirements

- Java 17
- Maven (for building and testing)
- Spring Boot

  ## Getting Started

  **Clone the repository:**
   ```bash
   git clone <[repository-url](https://github.com/jaywantkamble/retailStoreDiscountCalculatorInDifferentCurrencies)> --branch assignmentBranch
  ```
  - Install dependencies:
    ```bash
     mvn install
    ```
   - Running the Application
      ```bash
       mvn spring-boot:run
     ```
   - Running the Test cases
     ```bash
       mvn test
     ```

   ## UML Diagram 
![StoreDiscountAndCurrencyExchange](https://github.com/user-attachments/assets/e84fc4e6-f31b-4bef-b473-98c1238a4534)

## Code Coverage Report
![image](https://github.com/user-attachments/assets/cd6e40ca-9eaf-45d2-9e22-2a864085482c)

## SonarLint Code Analysis Report
![image](https://github.com/user-attachments/assets/71bb531e-5228-4858-8747-0bc4429e9e4a)




  

   

