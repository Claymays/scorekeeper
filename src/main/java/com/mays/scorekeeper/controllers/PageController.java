package com.mays.scorekeeper.controllers;

import com.mays.scorekeeper.entities.Friend;
import com.mays.scorekeeper.entities.Game;
import com.mays.scorekeeper.entities.User;
import com.mays.scorekeeper.services.GameService;
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
    public String home() {
        return "home";
    }

    @GetMapping("login")
    public String login() {
        return "login";
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
