package com.hsbc.codecheck.service;

import com.hsbc.codecheck.entity.AccountDocument;
import com.hsbc.codecheck.entity.CustomerDocument;
import com.hsbc.codecheck.model.Account;
import com.hsbc.codecheck.model.Customer;
import com.hsbc.codecheck.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Component
@Slf4j
public class CustomerService {

    @Autowired
    CustomerRepository repository;

    public String addCustomer(final Customer customer) {
        var customerDocument = buildCustomerDocument(customer);

        repository.insert(customerDocument);

        return customerDocument.getId();
    }

    public long addCustomerAccount(final String customerId, final Account account) {
        Optional<CustomerDocument> customerFromDB = repository.findById(customerId);

        if (customerFromDB.isPresent()) {
            var customerDocument = customerFromDB.get();
            var accounts = customerDocument.getAccounts();

            if (!ofNullable(accounts).isPresent()) {
                accounts = new ArrayList<>();
                customerDocument.setAccounts(accounts);
            }

            var accountDocument = buildAccountDocument(account);
            accounts.add(accountDocument);

            repository.save(customerDocument);

            return accountDocument.getAccountNo();
        } else {
            throw new RuntimeException("Customer not found in Database");
        }
    }

    public long getBalance(final String customerId, final long accountNo) {
        var customerDocument = repository.findByIdAndAccountNo(customerId, accountNo);

        if (ofNullable(customerDocument).isPresent()) {
            return customerDocument
                    .getAccounts()
                    .get(0)
                    .getBalance();
        } else {
            throw new RuntimeException("Customer/Account not found in Database");
        }
    }

    private CustomerDocument buildCustomerDocument(final Customer customer) {
        return CustomerDocument.builder()
                .id(UUID.randomUUID().toString())
                .name(customer.getName())
                .dob(customer.getDob())
                .address(customer.getAddress())
                .build();
    }

    private AccountDocument buildAccountDocument(final Account account) {
        return AccountDocument.builder()
                .accountNo(System.currentTimeMillis())
                .balance(account.getBalance())
                .startDate(account.getStartDate())
                .productType(account.getProductType())
                .transactions(account.getTransactions())
                .build();
    }
}
