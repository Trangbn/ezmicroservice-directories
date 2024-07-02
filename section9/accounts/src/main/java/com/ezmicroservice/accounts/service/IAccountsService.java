package com.ezmicroservice.accounts.service;

import com.ezmicroservice.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * create new account
     * @param customerDto
     */
    void  createAccount(CustomerDto customerDto);

    /**
     *Fetch customer by mobile number
     * @param mobileNumber
     * @return CustomerDto
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     * Update accounts
     * @param customerDto
     * @return
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * Delete account based on mobileNumber
     * @param mobileNumber
     * @return
     */
    boolean deleteAccount(String mobileNumber);
}
