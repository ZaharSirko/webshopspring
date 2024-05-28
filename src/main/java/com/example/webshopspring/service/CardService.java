package com.example.webshopspring.service;

import com.example.webshopspring.model.Card;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.model.User;
import com.example.webshopspring.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {
    final private CardRepository cardRepository;
    private final GoodService goodService;
    private final UserService userService;
    private final PriceService priceService;

    @Autowired
    public CardService(CardRepository cardRepository, GoodService goodService, UserService userService, PriceService priceService) {
        this.cardRepository = cardRepository;
        this.goodService = goodService;
        this.userService = userService;
        this.priceService = priceService;
    }

    public Card getCardByUserEmail(String email) {
        return cardRepository.findCardByUserEmail(email).orElse(null);
    }

//    public boolean addGoodToUserCard(Long id, String userEmail){
//        Good good = goodService.getGoodById(id);
//        Price price = priceService.getPriceForGoodId(good.getId());
//        User user =userService.getUserByEmail(userEmail);
//        if (user != null){
//            Card newCard = new Card();
//            newCard.setUser(user);
//            newCard.setPrice(price);
//            cardRepository.save(newCard);
//            return true;
//        }
//        return false;
//    }
    @Transactional
    public void removeGoodFromUserCard(Long id){
        cardRepository.deleteGoodFromUserCard(id);
    }
}
