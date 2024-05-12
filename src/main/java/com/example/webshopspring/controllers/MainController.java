package com.example.webshopspring.controllers;


import java.util.List;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("http://localhost:3000")
public class MainController {
final private GoodService goodService;asdas

    @Autowired
    public MainController(GoodService goodService) {
        this.goodService = goodService;
    }


    @GetMapping("/")
    public   List<Good> home() {
      return goodService.getAllGoods();
    }

    @GetMapping("/about")
    public void about() {

    }

}