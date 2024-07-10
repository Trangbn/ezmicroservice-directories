package com.ezmicroservice.accounts.service.impl;

import com.ezmicroservice.accounts.dto.AccountsDto;
import com.ezmicroservice.accounts.dto.CardsDto;
import com.ezmicroservice.accounts.dto.CustomerDetailsDto;
import com.ezmicroservice.accounts.dto.LoansDto;
import com.ezmicroservice.accounts.entity.Accounts;
import com.ezmicroservice.accounts.entity.Customer;
import com.ezmicroservice.accounts.exception.ResourceNotFoundException;
import com.ezmicroservice.accounts.mapper.AccountsMapper;
import com.ezmicroservice.accounts.mapper.CustomerMapper;
import com.ezmicroservice.accounts.repository.AccountsRepository;
import com.ezmicroservice.accounts.repository.CustomerRepository;
import com.ezmicroservice.accounts.service.ICustomerService;
import com.ezmicroservice.accounts.service.client.CardsFeignClient;
import com.ezmicroservice.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private LoansFeignClient loansFeignClient;
    private CardsFeignClient cardsFeignClient;

    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCards(correlationId, mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
