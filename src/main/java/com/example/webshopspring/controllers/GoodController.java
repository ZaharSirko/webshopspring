package com.example.webshopspring.controllers;

import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.service.GoodService;
import com.example.webshopspring.service.ImageService;
import com.example.webshopspring.service.PriceService;
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
    public  ResponseEntity<Good>  addNewGood(@ModelAttribute Good newGood,@RequestParam("imageFile") MultipartFile[] imageFile) throws IOException {
            String[] photosPath = new String[imageFile.length];
            for (int i = 0; i < imageFile.length; i++) {
                photosPath[i] = imageService.saveImage(imageFile[i]);
            }
          return new ResponseEntity<>( goodService.addGood(newGood.getGoodName(), newGood.getGoodDescription(), newGood.getGoodBrand(),
                  photosPath), HttpStatus.CREATED);
    }
}
