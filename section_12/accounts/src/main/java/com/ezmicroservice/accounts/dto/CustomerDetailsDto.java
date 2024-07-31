package com.ezmicroservice.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Cards, Accounts and Loans information"
)
public class CustomerDetailsDto {

    @NotEmpty(message = "Name is required")
    @Size(min = 5, max = 30, message = "The length should be between 5-30")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be a valid email")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Loans details of the Customer"
    )
    private LoansDto loansDto;

    @Schema(
            description = "Cards details of the Customer"
    )
    private CardsDto cardsDto;
}
