package com.hsbc.codecheck.controller;

import com.hsbc.codecheck.model.Account;
import com.hsbc.codecheck.model.Customer;
import com.hsbc.codecheck.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CodeCheckController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/customers")
    String createCustomer(@Valid @RequestBody Customer customer) {

        return customerService.addCustomer(customer);
    }

    @PutMapping("/customers/{customerId}/accounts")
    long addAccount(@PathVariable("customerId") String customerId,
                    @Valid @RequestBody Account account) {
        return customerService.addCustomerAccount(customerId, account);
    }

    @GetMapping("/customers/{customerId}/{accountNo}/balance")
    long getAccountBalance(@PathVariable("customerId") String customerId,
                           @PathVariable("accountNo") long accountNo) {

        return customerService.getBalance(customerId, accountNo);
    }
}
