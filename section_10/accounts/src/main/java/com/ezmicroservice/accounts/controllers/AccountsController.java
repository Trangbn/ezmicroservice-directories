package com.ezmicroservice.accounts.controllers;

import com.ezmicroservice.accounts.constants.AccountsConstants;
import com.ezmicroservice.accounts.dto.AccountsContactInfoDto;
import com.ezmicroservice.accounts.dto.CustomerDto;
import com.ezmicroservice.accounts.dto.ResponseDto;
import com.ezmicroservice.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "API microservice API",
        description = "Practice Microservice API"
)
public class AccountsController {

    private final IAccountsService accountsService;


    /**
     * This is for read environment properties not for properties in application.yml
     * Disadvantage: read one variable at a time, and need to hard code variable name
     * Same disadvantage for @Value
     */
    @Autowired
    private Environment env;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    public AccountsController(IAccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Operation(
            summary = "Create new accounts",
            description = "create new accounts"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Get accounts information",
            description = "Get accounts info"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status fetched successfully"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                        @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should be 10 digits")
                        String mobileNumber) {
        CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Update accounts",
            description = "update accounts"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status updated"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountsDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);

        return isUpdated ? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
    }

    @Operation(
            summary = "Delete accounts",
            description = "Delete accounts"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status deleted successfully"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails( @RequestParam
                                                                @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should be 10 digits")
                                                                String mobileNumber ){
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        return isDeleted ? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
    }

    @Operation(
            summary = "Get build information",
            description = "Get build info"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status OK"
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }


    @Operation(
            summary = "Get java version",
            description = "Get java version"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status OK"
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(env.getProperty("MAVEN_HOME"));
//        return ResponseEntity.status(HttpStatus.OK).body(env.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get contact details",
            description = "Get contact details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status OK"
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
    }
}
