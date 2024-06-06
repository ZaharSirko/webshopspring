package com.example.webshopspring.DTO.mapper;

import com.example.webshopspring.DTO.CardDTO;
import com.example.webshopspring.model.Card;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardMapper {

    public static CardDTO toDTO(Card card) {
        return new CardDTO(
                card.getId(),
                GoodWithPriceMapper.toDTO(card.getPrice().getGood_id(), card.getPrice())
        );
    }

    public static List<CardDTO> toListDTO(List<Card> cards) {
        return cards.stream()
                .map(CardMapper::toDTO)
                .collect(Collectors.toList());
    }
}
