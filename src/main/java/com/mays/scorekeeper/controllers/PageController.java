package com.mays.scorekeeper.controllers;

import com.mays.scorekeeper.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;

    @GetMapping("home")
    public String home() {
        return "home";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @GetMapping("newGame")
    public String newGame(Model model) {
        return "gameSetup";
    }

    @GetMapping("users")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
}
