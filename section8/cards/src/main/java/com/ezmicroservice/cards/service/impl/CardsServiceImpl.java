package com.ezmicroservice.cards.service.impl;

import com.ezmicroservice.cards.constant.CardsConstants;
import com.ezmicroservice.cards.dto.CardsDto;
import com.ezmicroservice.cards.entity.Cards;
import com.ezmicroservice.cards.exception.CardAlreadyExistException;
import com.ezmicroservice.cards.exception.ResourceNotFoundException;
import com.ezmicroservice.cards.mapper.CardsMapper;
import com.ezmicroservice.cards.repository.CardsRepository;
import com.ezmicroservice.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    @Override
    public void createCards(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistException("Card already represented with mobileNumber: " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardsDto fetchCards(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards info", "Mobile Number", mobileNumber)
        );
        return CardsMapper.maptoCardsDto(cards, new CardsDto());
    }

    @Override
    public boolean updateCards(CardsDto cardsDto) {
        Cards foundCard = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Cards info", "Card Number", cardsDto.getCardNumber())
        );
        Cards cards = CardsMapper.mapToCards(cardsDto, foundCard);
        cardsRepository.save(foundCard);
        return true;
    }

    @Override
    public boolean deleteCards(String mobileNumber) {
        Cards foundCard = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards info", "Card Number", mobileNumber)
        );
        cardsRepository.deleteById(foundCard.getCardId());
        return true;
    }
}
