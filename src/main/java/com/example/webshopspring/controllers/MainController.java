package com.example.webshopspring.controllers;


import java.util.List;

import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
//@CrossOrigin("http://localhost:3000")
public class MainController {
final private GoodService goodService;

    @Autowired
    public MainController(GoodService goodService) {
        this.goodService = goodService;
    }



    @GetMapping("/")
    public ResponseEntity<List<GoodWithPriceDTO>> getAllGoods() {
        List<GoodWithPriceDTO> goodsWithPrices = goodService.getAllGoodsWithPrices();
        return ResponseEntity.ok(goodsWithPrices);
    }


    @GetMapping("/about")
    public void about() {

    }

}