package com.example.webshopspring.controllers;


import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.webshopspring.model.User;
import com.example.webshopspring.service.UserService;


@Controller
public class MainController {
	private final UserService userService;

	public MainController(UserService userService){    
		this.userService= userService;
	}
	@GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Отримуємо ім'я користувача (username) з аутентифікованого контексту

        if (!username.equals("anonymousUser")) { // Перевіряємо, чи користувач аутентифікований
            User user = userService.getUserByEmail(username); // Отримуємо користувача за ім'ям
            model.addAttribute("user", user); // Додаємо користувача до моделі
        }

        model.addAttribute("title", "Main Page");
        return "home";
    }



}