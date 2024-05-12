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

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class PriceController {
    final private PriceService priceService;
    private final GoodService goodService;

    @Autowired
    public PriceController(PriceService priceService, GoodService goodService) {
        this.priceService = priceService;
        this.goodService = goodService;
    }
    @GetMapping("/price/add")
    public List<Good> price() {
         return  goodService.getAllGoods();
    }
    @PostMapping("/price/add")
    public void add(@ModelAttribute Price price) {
            priceService.addPrice(price);
    }

    @GetMapping("/price/edit/{priceId}")
    public void price(@PathVariable Long priceId) {
        Price price = priceService.getPriceById(priceId);
        List<Good> good = goodService.getAllGoods();
    }

    @PostMapping("/price/edit/{priceId}")
    public boolean edit(@PathVariable Long priceId, @ModelAttribute Price updatedPrice) {
        return  priceService.updatePrice(priceId, updatedPrice);
    }
}
