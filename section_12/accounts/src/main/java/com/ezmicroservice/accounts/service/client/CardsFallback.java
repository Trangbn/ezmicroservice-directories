package com.ezmicroservice.accounts.service.client;

import com.ezmicroservice.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> fetchCards(String correlationId, String mobileNumber) {
        return null;
    }
}
