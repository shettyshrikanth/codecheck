package com.hsbc.codecheck.service;

import com.hsbc.codecheck.entity.AccountDocument;
import com.hsbc.codecheck.entity.CustomerDocument;
import com.hsbc.codecheck.model.Account;
import com.hsbc.codecheck.model.Customer;
import com.hsbc.codecheck.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    @Test
    void addCustomer() {
        service.addCustomer(new Customer());

        verify(repository).insert(any(CustomerDocument.class));
    }

    @Test
    void addAccountToNewCustomer() {
        var customerId = "cust-1";
        var customerDocument = CustomerDocument.builder().id(customerId).build();

        given(repository.findById(customerId)).willReturn(Optional.of(customerDocument));

        var accountNo = service.addCustomerAccount(customerId, new Account());

        assertNotNull(accountNo);
        assertEquals(1, customerDocument.getAccounts().size());
        verify(repository).findById(customerId);
        verify(repository).save(any(CustomerDocument.class));
    }

    @Test
    void addMoreAccountToCustomer() {
        var customerId = "cust-1";
        List<AccountDocument> accounts = new ArrayList<>();
        accounts.add(AccountDocument.builder().accountNo(1234).build());
        var customerDocument = CustomerDocument.builder().id(customerId).build();
        customerDocument.setAccounts(accounts);

        given(repository.findById(customerId)).willReturn(Optional.of(customerDocument));

        var accountNo = service.addCustomerAccount(customerId, new Account());

        assertNotNull(accountNo);
        assertEquals(2, customerDocument.getAccounts().size());
        verify(repository).findById(customerId);
        verify(repository).save(any(CustomerDocument.class));
    }


    @Test
    void addAccountWithNoCustomer() {
        var customerId = "cust-1";
        given(repository.findById(customerId)).willReturn(Optional.empty());

        var thrown = assertThrows(
                RuntimeException.class,
                () -> service.addCustomerAccount(customerId, new Account()),
                "Expected addCustomerAccount() to throw RuntimeException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Customer not found in Database"));
        verify(repository, times(0)).save(any(CustomerDocument.class));
    }


    @Test
    void getBalance() {
        var customerId = "cust-1";
        var accountNo = 1234;
        var balance = 1100;
        List<AccountDocument> accounts = new ArrayList<>();
        accounts.add(AccountDocument.builder().accountNo(accountNo).balance(balance).build());
        var customerDocument = CustomerDocument.builder().id(customerId).build();
        customerDocument.setAccounts(accounts);

        given(repository.findByIdAndAccountNo(customerId, accountNo)).willReturn(customerDocument);

        long accountBalance = service.getBalance(customerId, accountNo);

        assertEquals(balance, accountBalance);
        verify(repository).findByIdAndAccountNo(customerId, accountNo);
    }

    @Test
    void getBalanceForNonExistingCustomer() {
        var customerId = "cust-1";
        var accountNo = 1234;
        given(repository.findByIdAndAccountNo(customerId, accountNo)).willReturn(null);

        var thrown = assertThrows(
                RuntimeException.class,
                () -> service.getBalance(customerId, accountNo),
                "Expected getBalance() to throw RuntimeException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Customer/Account not found in Database"));
    }
}