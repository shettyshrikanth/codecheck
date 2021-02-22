package com.hsbc.codecheck.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDocument {
    long accountNo;
    String startDate;
    long balance;
    String transactions;
    String productType;
}
