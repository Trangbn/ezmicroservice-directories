package com.ezmicroservice.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Error message in response"
)
public class ErrorResponseDto {

    @Schema(
            description = "API path invoked by client"
    )
    private String apiPath;

    @Schema(
            description = "Error code in response"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message in response"
    )
    private String errorMessage;

    @Schema(
            description = "Time representing when error happens"
    )
    private LocalDateTime errorTime;
}
