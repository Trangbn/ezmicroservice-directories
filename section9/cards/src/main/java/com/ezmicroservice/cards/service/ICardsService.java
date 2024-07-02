package com.ezmicroservice.cards.service;

import com.ezmicroservice.cards.dto.CardsDto;
import com.ezmicroservice.cards.entity.Cards;

public interface ICardsService {
     void createCards(String mobileNumber);
     CardsDto fetchCards(String mobileNumber);
     boolean updateCards(CardsDto cardsDto);
     boolean deleteCards(String mobileNumber);
}
