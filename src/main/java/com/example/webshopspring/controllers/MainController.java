package com.example.webshopspring.controllers;


import java.security.Principal;
import java.util.List;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.webshopspring.model.User;
import com.example.webshopspring.service.UserService;


@Controller
public class MainController {
final private GoodService goodService;

    @Autowired
    public MainController(GoodService goodService) {
        this.goodService = goodService;
    }


    @GetMapping("/")
    public String home(Model model) {
    List<Good> goodList = goodService.getAllGoods();

        model.addAttribute("goods", goodList);
        model.addAttribute("title", "Main Page");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

}