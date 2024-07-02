package com.ezmicroservice.accounts.controllers;

import com.ezmicroservice.accounts.dto.CustomerDetailsDto;
import com.ezmicroservice.accounts.mapper.CustomerMapper;
import com.ezmicroservice.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST Api for Customer in EZmicroservice",
        description = "Rest Api to fetch customer details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestParam
                                                                   @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should be 10 digits")
                                                                   String phoneNumber) {
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }

}
