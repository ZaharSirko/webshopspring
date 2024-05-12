package com.example.webshopspring.controllers;

import com.example.webshopspring.model.Card;
import com.example.webshopspring.service.CardService;
import com.example.webshopspring.service.GoodService;
import com.example.webshopspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;


@RestController
@CrossOrigin("http://localhost:3000")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService, GoodService goodService, UserService userService) {
        this.cardService = cardService;
    }

    @GetMapping("/card/{user_email}")
    public Card viewCard(@PathVariable("user_email") String userEmail) {
       return cardService.getCardByUserEmail(userEmail);
    }

 @PostMapping("/good/{id}")
    public boolean addGoodToUserCard(@PathVariable("id") Long id, Principal principal) {
       return  cardService.addGoodToUserCard(id, principal.getName());
 }

    @PostMapping(value = "/card/{userName}/cancel/{cardId}")
    public void cancelTourReservation( @PathVariable String userName,@PathVariable Long cardId ) {
        cardService.removeGoodFromUserCard(cardId);
    }

}
