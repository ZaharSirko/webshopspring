package com.example.webshopspring.controllers;

import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.model.User;
import com.example.webshopspring.service.GoodService;
import com.example.webshopspring.service.ImageService;
import com.example.webshopspring.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
public class GoodController {
    public final GoodService goodService;
    public final ImageService imageService;

    @Autowired
    public GoodController(GoodService goodService, PriceService priceService, ImageService imageService) {
        this.goodService = goodService;
        this.imageService = imageService;
    }

    @GetMapping("/")
    public ResponseEntity<List<GoodWithPriceDTO>> getAllGoods() {
        List<GoodWithPriceDTO> goodsWithPrices = goodService.getAllGoodsWithPrices();
        return new ResponseEntity<>(goodsWithPrices, HttpStatus.OK);
    }

    @GetMapping("/good/{id}")
    public ResponseEntity<GoodWithPriceDTO> getGoodWithPriceById(@PathVariable("id") Long id) {
        GoodWithPriceDTO goodWithPriceDTO = goodService.getGoodWithPriceById(id);
       return new ResponseEntity<>(goodWithPriceDTO, HttpStatus.OK);
    }

    @PostMapping("/good/add")
    public ResponseEntity<Good> addNewGood(@ModelAttribute  Good newGood,
                                           @RequestParam("imageFile") MultipartFile[] imageFiles) throws IOException {
        String[] photosPath = new String[imageFiles.length];
        for (int i = 0; i < imageFiles.length; i++) {
            photosPath[i] = imageService.saveImage(imageFiles[i]);
        }
        Good addedGood = goodService.addGood(newGood.getGoodName(), newGood.getGoodDescription(), newGood.getGoodBrand(), photosPath);
        return new ResponseEntity<>(addedGood, HttpStatus.CREATED);
    }


    @GetMapping("/good/search")
    public List<Long> searchGoods(@RequestParam String query) {
        if (Objects.equals(query, "")) {
            return null;
        }
        return goodService.searchGoods(query);
    }
    @GetMapping("/good/search-suggestions")
    public List<String> getSearchSuggestions(@RequestParam String query) {
        if (query == null) {
            return null;
        }
        return goodService.getSearchSuggestions(query);
    }
}
