package com.example.webshopspring.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import com.example.webshopspring.service.GoodService;
import com.example.webshopspring.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController {
final private GoodService goodService;
final private PriceService priceService;
    @Autowired
    public MainController(GoodService goodService, PriceService priceService) {
        this.goodService = goodService;
        this.priceService = priceService;
    }


    @GetMapping("/")
    public String home(Model model) {
    List<Good> goodList = goodService.getAllGoods();
        Map<Long, List<Price>> goodPriceMap = new HashMap<>();
        for (Good good : goodList) {
            List<Price> goodPrice = priceService.getAllPriceForGood(good);
            goodPriceMap.put(good.getId(), goodPrice);
        }
        model.addAttribute("goods", goodList);
        model.addAttribute("goodPrice", goodPriceMap);
        model.addAttribute("title", "Main Page");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

}