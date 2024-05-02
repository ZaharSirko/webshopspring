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

@Controller
public class GoodController {
    public final GoodService goodService;
    public final PriceService priceService;

    @Autowired
    public GoodController(GoodService goodService, PriceService priceService) {
        this.goodService = goodService;
        this.priceService = priceService;
    }

    @GetMapping("/good/{id}")
    public String getGoodById(@PathVariable("id") Long id, Model model) {
        Good good = goodService.getGoodById(id);
        if (good != null) {
            model.addAttribute("good", good);
            return "good-detail";
        } else {
            return "redirect:/";
        }
    }


    @GetMapping("/good/add")
    public String addNewGood(Model model) {
        List<Price> price = priceService.getAllPrices();
        model.addAttribute("good", new Good());
        model.addAttribute("price",price);
        return "good-add";
    }

    @PostMapping("/good/add")
    public String addNewGood(@ModelAttribute Good newGood, BindingResult bindingResult,@RequestParam("imageFile") MultipartFile[] imageFile) throws IOException {

        if (bindingResult.hasErrors()) {
            return "redirect:/about";
        }
        else{
            String[] photosPath = new String[imageFile.length];
            for (int i = 0; i < imageFile.length; i++) {
                photosPath[i] = goodService.saveImage(imageFile[i]);
            }
            goodService.addGood(newGood.getGoodName(), newGood.getGoodDescription(), newGood.getGoodBrand(),
                    photosPath,  newGood.getGoodPrice());
            return "redirect:/";
        }
    }
}
