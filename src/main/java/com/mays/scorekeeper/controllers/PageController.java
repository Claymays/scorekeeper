package com.mays.scorekeeper.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PageController {

    private final InMemoryUserDetailsManager manager;

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

    @PostMapping("authentication")
    public String authenticate(UserRequestBody newUser) {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(newUser.getUsername())
                .password(newUser.getPassword())
                .roles("USER")
                .build();
        manager.createUser(user);
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
