package com.ezmicroservice.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Cards",
        description = "Schema to hold Cards information"
)
@Data
public class CardsDto {

    @NotEmpty(message = "Mobile number is required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber should be 10 digits")
    @Schema(
            description = "Mobile number of Customer" , example = "0969855555"
    )
    private String mobileNumber;

    @NotEmpty(message = "Card number is required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Card number should be 10 digits")
    private String cardNumber;

    @NotEmpty(message = "Card type is required")
    private String cardType;

    @Positive(message = "Total card should be greater than zero")
    private int totalLimit;

    @PositiveOrZero(message = "Used amount must be positive or zero")
    private int amountUsed;

    @PositiveOrZero(message = "Available amount must be positive or zero")
    private int availableAmount;
}
