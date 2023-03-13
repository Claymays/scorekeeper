package com.mays.scorekeeper.controllers;

import com.mays.scorekeeper.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    UserService userService;

    @GetMapping()
    public String hello() {
        return "hello";
    }

    @GetMapping("users")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("login")
    public String getLogin() {
        return "login";
    }
}
