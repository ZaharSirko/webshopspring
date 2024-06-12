package com.example.webshopspring.controllers;

import com.example.webshopspring.DTO.CardDTO;
import com.example.webshopspring.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;


@RestController
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }



    @GetMapping("/profile/card")
    public ResponseEntity<List<CardDTO>> viewCard(Principal principal) {
        String email = principal.getName();
        List<CardDTO> cardDTOs = cardService.getCardListDTOByUserEmail(email);
        return new ResponseEntity<>(cardDTOs, HttpStatus.OK);
    }


    @PostMapping("/good/{id}")
    public ResponseEntity<Boolean> addGoodToUserCard(@PathVariable("id") Long id, Principal principal) {
       boolean isAddedToCard = cardService.addGoodToUserCard(id, principal.getName());
       return new ResponseEntity<>(isAddedToCard, HttpStatus.OK);
     }

    @PostMapping(value = "/profile/card/cancel/{cardId}")
    public void cancelGoodFormCard(@PathVariable Long cardId ) {
        cardService.removeGoodFromUserCard(cardId);
    }

}
