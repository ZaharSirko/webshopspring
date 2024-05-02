package com.example.webshopspring.controllers;

import com.example.webshopspring.model.Card;
import com.example.webshopspring.service.CardService;
import com.example.webshopspring.service.GoodService;
import com.example.webshopspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.security.Principal;


@Controller
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService, GoodService goodService, UserService userService) {
        this.cardService = cardService;
    }

    @GetMapping("/card/{user_email}")
    public String viewCard(@PathVariable("user_email") String userEmail, Model model) {
       Card card = cardService.getCardByUserEmail(userEmail);
        model.addAttribute("card", card);
        model.addAttribute("title", "Card Page");
        return "card-detail";
    }

 @PostMapping("/good/{id}")
    public String addGoodToUserCard(@PathVariable("id") Long id, Principal principal) {
         cardService.addGoodToUserCard(id, principal.getName());
         return "redirect:/card/" + principal.getName();

 }

    @PostMapping(value = "/card/{userName}/cancel/{cardId}")
    public String cancelTourReservation( @PathVariable String userName,@PathVariable Long cardId ) {
        cardService.removeGoodFromUserCard(cardId);
        return "redirect:/card/" + userName;
    }

}
