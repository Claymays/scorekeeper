package com.mays.scorekeeper.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A Javabean representing a game object, for tracking gamestate.
 *
 * @author Clayton Mays
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Team> teams;

    /**
     * Partially qualified class constructor.
     *  Instantiates properties with blank values for form handling
     * @param owner User object that created the game
     */
    public Game(User owner) {
        this.teams = new ArrayList<>();
        this.teams.add(new Team(this, 0));
        this.owner = owner;
        this.name = "";
    }
}
