package com.mays.scorekeeper.controllers;

import com.mays.scorekeeper.entities.Game;
import com.mays.scorekeeper.entities.Team;
import com.mays.scorekeeper.entities.User;
import com.mays.scorekeeper.services.GameService;
import com.mays.scorekeeper.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * Landing page for unauthenticated users.
     *  redirect if the user is auth'ed in the session
     * @return landing page
     */
    @GetMapping
    public String landing() {
        Optional<User> user = userService.getUserFromContext();
        if (user.isPresent()) {
            return "redirect:/profile";
        }
        return "landing";
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
    @PostMapping("register")
    public String createUser(UserRequestBody newUser) {
        userService.create(newUser.getUsername(), newUser.getPassword());
        return "redirect:/login";
    }

    /**
     * Returns the view for a user's profile
     * @param model The model containing attributes of the view 
     * @return User's profile, unless the user is not authenticated
     */
    @GetMapping("profile")
    public String profile(Model model) {
        Optional<User> user = userService.getUserFromContext();
        if (user.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("user", user.get());
        return "profile";
    }

    /**
     * Creates a new Game object and attaches it to the view,
     * then returns a view for game configuration
     * @param model The model containing attributes of the view
     * @return
     */
    @GetMapping("game/new")
    public String newGame(Model model) {
        Optional<User> user = userService.getUserFromContext();
        if (user.isEmpty()) {
            return "redirect:/login";
        }
        Game game = new Game(user.get());
        model.addAttribute("game", game);
        model.addAttribute("user", user.get());
        return "gameSetup";
    }

    /**
     * Endpoint that consumes a completed game form.
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

    /**
     * Form method, attaches a team to the game object.
     * @param game form object to attach a team to.
     * @return re-renders the game setup view with additional team
     */
    @RequestMapping(value="/game/new/submit", params={"addTeam"})
    public String addTeam(final Game game) {
        game.getTeams().add(new Team(game, game.getTeams().size()));
        return "gameSetup";
    }

    /**
     * Form method that updates each teams score.
     * @param game Game object to update
     * @return re-render the game view with updated scores
     */
    @RequestMapping(value ="/game/update", params={"updateScores"})
    public String updateScores(final Game game) {
        for (Team team : game.getTeams()) {
            team.getScores().add(team.getNewScore());
            team.setNewScore(0);
            team.calculateTotalScore();
        }
        return "game";
    }

    @GetMapping("game/{gameId}")
    public String getGame(@PathVariable int gameId, Model model) {
        model.addAttribute("game", gameService.get(gameId).get());
        return "game";
    }

    /**
     * Endpoint for deleting game record
     * @param gameId id of the record to be deleted
     * @return the user's profile page
     */
    @GetMapping("game/delete/{gameId}")
    public String deleteGame(@PathVariable int gameId) {
        gameService.delete(gameId);
        return "redirect:/profile";
    }
}
