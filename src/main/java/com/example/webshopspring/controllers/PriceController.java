package com.example.webshopspring.controllers;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.service.GoodService;
import com.example.webshopspring.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @GetMapping("/price/edit/{priceId}")
    public ResponseEntity<Price> price(@PathVariable Long priceId) {
        Price price = priceService.getPriceById(priceId);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }

    @PutMapping("/price/edit/{priceId}")
    public ResponseEntity<Boolean> updatePrice(@PathVariable Long priceId, @RequestBody Price updatedPrice) {
        boolean isUpdated = priceService.updatePrice(priceId, updatedPrice);
        if (isUpdated) {
            return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
