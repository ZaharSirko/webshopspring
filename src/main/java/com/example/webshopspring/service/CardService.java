package com.example.webshopspring.service;

import com.example.webshopspring.model.Card;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.User;
import com.example.webshopspring.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Card getCardByUserEmail(String email) {
        return cardRepository.findCardByUserEmail(email).orElse(null);
    }

public boolean addGoodToUserCard(Long id, String userEmail){
    Good good = goodService.getGoodById(id);
    User user =userService.getUserByEmail(userEmail);
    if (user != null){
        Card newCard = new Card();
        newCard.setUser(user);
        newCard.setGood(good);
         cardRepository.save(newCard);
         return true;
    }
       return false;
}
public boolean removeGoodFromUserCard(Long id, String userEmail){
        Good good = goodService.getGoodById(id);
        User user =userService.getUserByEmail(userEmail);
        if (user != null && good != null){
           cardRepository.deleteGoodFromUserCard(good.getId(),user.getId());
            return true;
        }
        return false;
}
}
