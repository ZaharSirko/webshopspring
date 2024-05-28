package com.example.webshopspring.controllers;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.service.GoodService;
import com.example.webshopspring.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class GoodController {
    public final GoodService goodService;
    public final PriceService priceService;

    @Autowired
    public GoodController(GoodService goodService, PriceService priceService) {
        this.goodService = goodService;
        this.priceService = priceService;
    }

    @GetMapping("/good/{id}")
    public Good getGoodById(@PathVariable("id") Long id) {
       return goodService.getGoodById(id);
//        Good good = goodService.getGoodById(id);
////        if (good != null) {
////            return  priceService.getPriceForGoodId(good.getId());
////        }
    }


    @GetMapping("/good/add")
    public List<Price> addNewGood() {
        return   priceService.getAllPrices();
//        model.addAttribute("good", new Good());
//        model.addAttribute("price",price);
    }

    @PostMapping("/good/add")
    public Good addNewGood(@ModelAttribute Good newGood,@RequestParam("imageFile") MultipartFile[] imageFile) throws IOException {
            String[] photosPath = new String[imageFile.length];
            for (int i = 0; i < imageFile.length; i++) {
                photosPath[i] = goodService.saveImage(imageFile[i]);
            }
            return   goodService.addGood(newGood.getGoodName(), newGood.getGoodDescription(), newGood.getGoodBrand(),
                    photosPath);
    }
}
