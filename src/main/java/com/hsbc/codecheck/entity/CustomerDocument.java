package com.hsbc.codecheck.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerDocument {
    String id;
    String name;
    String dob;
    String address;
    List<AccountDocument> accounts;
}
