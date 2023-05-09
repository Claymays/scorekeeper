package com.mays.scorekeeper.controllers;

import com.mays.scorekeeper.entities.Game;
import com.mays.scorekeeper.entities.Team;
import com.mays.scorekeeper.entities.User;
import com.mays.scorekeeper.services.GameService;
import com.mays.scorekeeper.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Controller for handling view and model display for application
 *
 * @author Clayton Mays
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;
    private final GameService gameService;

    /**
     * Static class for basic authentication/authorization transactions
     */
    @Data
    private static class UserRequestBody {
        String username;
        String password;
    }

    /**
     * Access point for registration view
     * @param model The model containing attributes of the view
     * @return
     */
    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("user", new UserRequestBody());
        return "register";
    }

    /**
     * Endpoint for registering a new user from form data
     * @param newUser the user to create a record for
     * @return a redirect to the login page
     */
    @PostMapping("user/new")
    public String createUser(UserRequestBody newUser) {
        userService.create(newUser.getUsername(), newUser.getPassword());
        return "redirect:/login";
    }

    /**
     *
     * @return
     */
    @GetMapping
    public String landing() {
        return "landing";
    }

    /**
     *
     * @param model The model containing attributes of the view
     * @return
     */
    @GetMapping("game/new")
    public String newGame(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Optional<User> user = userService.getByUsername(username);
                if (user.isEmpty()) {
                    return "redirect:/error";
                }
                Game game = new Game(user.get());
                game.getTeams().add(new Team(game, game.getTeams().size()));
                model.addAttribute("game", game);
                model.addAttribute(user.get());
            }
        }
        return "gameSetup";
    }

    /**
     *
     * @param game form submitted game object
     * @return game view
     */
    @PostMapping("game/new/submit")
    public String submitGame(Game game) {
        User user = game.getOwner();
        gameService.create(game).get();
        user.getGames().add(game);
        userService.update(user);

        return "game";
    }

    @RequestMapping(value="/game/new/submit", params={"addTeam"})
    public String addTeam(final Game game, final BindingResult bindingResult) {
        game.getTeams().add(new Team(game, game.getTeams().size()));
        return "gameSetup";
    }

    @RequestMapping(value ="/game/update", params={"updateScores"})
    public String updateScores(final Game game, final BindingResult bindingResult) {
        for (Team team : game.getTeams()) {
            team.getScores().add(team.getNewScore());
            team.setNewScore(0);
            team.calculateTotalScore();
        }
        return "game";
    }
}
