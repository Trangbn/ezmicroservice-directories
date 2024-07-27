package com.ezmicroservice.accounts.controllers;

import com.ezmicroservice.accounts.dto.CustomerDetailsDto;
import com.ezmicroservice.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST Api for Customer in EZmicroservice",
        description = "Rest Api to fetch customer details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
                                                                    @RequestHeader("ezmicroservice-correlation-id")
                                                                    String correlationId,
                                                                    @RequestParam
                                                                    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should be 10 digits")
                                                                    String mobileNumber) {

        logger.debug("fetchCustomerDetail method start");
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        logger.debug("fetchCustomerDetail method end");
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }

}
