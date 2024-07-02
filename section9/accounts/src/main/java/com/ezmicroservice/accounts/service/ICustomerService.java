package com.ezmicroservice.accounts.service;

import com.ezmicroservice.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    /**
     *
     * @param phoneNumber
     * @return
     */
    CustomerDetailsDto fetchCustomerDetails(String phoneNumber);

}
