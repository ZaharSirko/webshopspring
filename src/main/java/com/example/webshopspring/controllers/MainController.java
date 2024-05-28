package com.example.webshopspring.controllers;


import java.util.List;

import com.example.webshopspring.DTO.GoodWithPriceDTO;
import com.example.webshopspring.model.Good;
import com.example.webshopspring.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {


    @GetMapping("/about")
    public void about() {

    }

}