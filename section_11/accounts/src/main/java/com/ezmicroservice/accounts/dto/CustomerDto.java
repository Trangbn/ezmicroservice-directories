package com.ezmicroservice.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

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
}
