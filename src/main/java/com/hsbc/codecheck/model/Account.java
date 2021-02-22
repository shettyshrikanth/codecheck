package com.hsbc.codecheck.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @NotBlank
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")
    String startDate;

    @NotNull
    long balance;

    @NotBlank
    String transactions;

    @Pattern(regexp = "current|saving")
    String productType;
}
