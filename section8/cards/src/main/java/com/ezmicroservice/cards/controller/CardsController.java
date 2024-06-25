package com.ezmicroservice.cards.controller;

import com.ezmicroservice.cards.constant.CardsConstants;
import com.ezmicroservice.cards.dto.CardsContactInfoDto;
import com.ezmicroservice.cards.dto.CardsDto;
import com.ezmicroservice.cards.dto.ErrorResponseDto;
import com.ezmicroservice.cards.dto.ResponseDto;
import com.ezmicroservice.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Cards API",
        description = "CRUD for Cards in Ez microservice"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

    private ICardsService cardService;

    public CardsController(ICardsService cardsService) {
        this.cardService = cardsService;
    }

    @Autowired
    private CardsContactInfoDto contactInfoDto;

    @Operation(
            summary = "Create cards REST API",
            description = "Create cards"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCards(@Valid @RequestParam
                                                   @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                   String mobileNumber) {
        cardService.createCards(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }


    @Operation(
            summary = "Fetch Cards REST API",
            description = "REST API to fetch card detail"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status ok"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal server error"
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCards(@Valid @RequestParam
                                                   @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                   String mobileNumber) {
        CardsDto cardsDto = cardService.fetchCards(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);

    }

    @Operation(
            summary = "Update cards REST API",
            description = "REST API to update cards"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTPS status ok"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCards(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = cardService.updateCards(cardsDto);
        return isUpdated ? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCards(@Valid @RequestParam
                                                       @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                       String mobileNumber){
        boolean isDeleted = cardService.deleteCards(mobileNumber);
        return isDeleted ? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
    }

    @GetMapping("/cards-info")
    public ResponseEntity<CardsContactInfoDto> getCardInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(contactInfoDto);
    }
}
