package com.example.webshopspring.controllers;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.service.GoodService;
import com.example.webshopspring.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PriceController {
    final private PriceService priceService;
    private final GoodService goodService;

    @Autowired
    public PriceController(PriceService priceService, GoodService goodService) {
        this.priceService = priceService;
        this.goodService = goodService;
    }
    @GetMapping("/price/add")
    public ResponseEntity<List<Good>> getPriceForm() {
        List<Good> good = goodService.getGoodsWitOutPrice();
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @PostMapping("/price/add")
    public ResponseEntity<Price> addNewPrice(@RequestBody Price price) {
        if (price == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
         Price addedPrice = priceService.addPrice(price);
            return new ResponseEntity<>(addedPrice, HttpStatus.CREATED);
        }
    }

//    @GetMapping("/price/edit/{priceId}")
//    public void price(@PathVariable Long priceId) {
//        Price price = priceService.getPriceById(priceId);
//        List<Good> good = goodService.getAllGoods();
//    }
//
//    @PostMapping("/price/edit/{priceId}")
//    public boolean edit(@PathVariable Long priceId, @ModelAttribute Price updatedPrice) {
//        return  priceService.updatePrice(priceId, updatedPrice);
//    }
}
