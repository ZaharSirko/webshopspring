package com.example.webshopspring.service;

import com.example.webshopspring.DTO.CardDTO;
import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.DTO.UserDTO;
import com.example.webshopspring.DTO.mapper.CardMapper;
import com.example.webshopspring.DTO.mapper.GoodWithPriceMapper;
import com.example.webshopspring.DTO.mapper.UserMapper;
import com.example.webshopspring.model.Card;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardService {
    final private CardRepository cardRepository;
    private final GoodService goodService;
    private final UserService userService;

    @Autowired
    public CardService(CardRepository cardRepository, GoodService goodService, UserService userService) {
        this.cardRepository = cardRepository;
        this.goodService = goodService;
        this.userService = userService;
    }

    public boolean addGoodToUserCard(Long id, String userEmail){
        GoodWithPriceDTO good = goodService.getGoodWithPriceById(id);
        UserDTO user =userService.getUserByEmail(userEmail);
        if (user != null){
            Card newCard = new Card();
            newCard.setUser(UserMapper.toUserId(user));
            newCard.setPrice(GoodWithPriceMapper.toPriceId(good));
            cardRepository.save(newCard);
            return true;
        }
        return false;
    }

    public List<CardDTO> getCardListDTOByUserEmail(String email) {
        List<Card> UserCard = cardRepository.findAllByUserEmail(email);
        return CardMapper.toListDTO(UserCard);
    }

    @Transactional
    public void removeGoodFromUserCard(Long id){
        cardRepository.deleteGoodFromUserCard(id);
    }
}
