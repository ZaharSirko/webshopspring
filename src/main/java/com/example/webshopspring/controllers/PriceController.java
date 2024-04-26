package com.example.webshopspring.controllers;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.service.GoodService;
import com.example.webshopspring.service.PriceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PriceController {
    final private PriceService priceService;
    private final GoodService goodService;

    public PriceController(PriceService priceService, GoodService goodService) {
        this.priceService = priceService;
        this.goodService = goodService;
    }
    @GetMapping("/price/add")
    public String price(Model model) {
        List<Good> good = goodService.getAllGoods();
        model.addAttribute("good",good);
        return "price-add";
    }
    @PostMapping("/price/add")
    public String add(@ModelAttribute Price price, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/about";
        } else {
            priceService.addPrice(price);
            return "redirect:/";
        }
    }
}
