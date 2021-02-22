package com.hsbc.codecheck.controller;

import com.google.gson.Gson;
import com.hsbc.codecheck.model.Account;
import com.hsbc.codecheck.model.Customer;
import com.hsbc.codecheck.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CodeCheckController.class)
class CodeCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @Test
    void createCustomer() throws Exception {
        var customerId = UUID.randomUUID().toString();
        var payload = new Customer("Rama", "1980-01-06", "12, WatfordRoad, Bangalore");

        when(service.addCustomer(any(Customer.class))).thenReturn(customerId);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(payload).getBytes()))
                .andExpect(status().isOk())
                .andExpect(content().string(customerId));
    }

    @Test
    void createCustomerWithFailedValidation() throws Exception {
        var payload = new Customer("Rama", "1980-01-36", "12, WatfordRoad, Bangalore");

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addAccount() throws Exception {
        var customerId = UUID.randomUUID().toString();
        var accountNo = System.currentTimeMillis();
        var payload = new Account("1980-01-06", 250, "Dummy Transaction", "saving");

        when(service.addCustomerAccount(any(String.class), any(Account.class))).thenReturn(accountNo);

        mockMvc.perform(put(format("/customers/%s/accounts", customerId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(payload)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(accountNo)));
    }

    @Test
    void addAccountWithFailedValidation() throws Exception {
        var customerId = UUID.randomUUID().toString();
        var payload = new Account("1980-01-06", 250, "Dummy Transaction", "dummy");

        mockMvc.perform(put(format("/customers/%s/accounts", customerId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAccountBalance() throws Exception {
        var customerId = UUID.randomUUID().toString();
        var accountNo = System.currentTimeMillis();
        var balance = 256L;

        when(service.getBalance(customerId, accountNo)).thenReturn(balance);

        mockMvc.perform(get(format("/customers/%s/%d/balance", customerId, accountNo)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(balance)));
    }
}