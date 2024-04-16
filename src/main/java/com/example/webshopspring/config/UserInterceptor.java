package com.example.webshopspring.config;

import java.security.Principal;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.webshopspring.model.User;
import com.example.webshopspring.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class UserInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public UserInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            User currentUser = userService.getUserByUserName(principal.getName());
            request.setAttribute("currentUser", currentUser);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (modelAndView != null && modelAndView.getViewName() != null) {
            Object currentUser = request.getAttribute("currentUser");
            if (currentUser != null) {
                modelAndView.addObject("user", currentUser);
            }
        }
    }
}
