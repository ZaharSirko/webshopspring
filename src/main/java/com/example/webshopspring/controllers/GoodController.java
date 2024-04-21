package com.example.webshopspring.controllers;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.service.GoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class GoodController {
    public final GoodService goodService;

    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }
    @GetMapping("/good/add")
    public String addTour() {
        return "good-add";
    }

    @PostMapping("/good/add")
    public String addNewTour(@ModelAttribute Good newGood, BindingResult bindingResult,@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
       //TO-DO IMAGE FOR GOOD
        if (bindingResult.hasErrors()) {
            return "redirect:/about";
        }
        else{
            goodService.addGood(newGood);
            return "redirect:/";
        }
    }
}
