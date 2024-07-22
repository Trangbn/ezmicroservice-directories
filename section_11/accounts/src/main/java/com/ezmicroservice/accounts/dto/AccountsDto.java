package com.ezmicroservice.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class AccountsDto {

    @NotEmpty(message = "AccountNumber is required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber should be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "AccountType is required")
    private String accountType;

    @NotEmpty(message = "Branch address is required")
    private String branchAddress;

}
