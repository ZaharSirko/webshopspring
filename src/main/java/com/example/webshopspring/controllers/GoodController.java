package com.example.webshopspring.controllers;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.service.GoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class GoodController {
    public final GoodService goodService;

    public GoodController(GoodService goodService) {
        this.goodService = goodService;
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
    public String addNewGood() {
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
                    photosPath);
//            newGood.getGoodPrice()
            return "redirect:/";
        }
    }
}
