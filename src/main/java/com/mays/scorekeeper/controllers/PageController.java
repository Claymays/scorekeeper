package com.mays.scorekeeper.controllers;

import com.mays.scorekeeper.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;

    @Data
    protected static class UserRequestBody {
        String username;
        String password;
    }


    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("user", new UserRequestBody());
        return "register";
    }

    @PostMapping("create-user")
    public String createUser(UserRequestBody newUser) {
        userService.create(newUser.getUsername(), newUser.getPassword());
        return "redirect:login";
    }

    @GetMapping
    public String profile(Principal principal) {
        return "profile";
    }

    @GetMapping("newGame")
    public String newGame(Model model, HttpServletRequest request) {
        return "gameSetup";
    }
}
