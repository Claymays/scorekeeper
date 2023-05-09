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

    @Column(name = "note")
    private String note;

    @ManyToOne
    private User owner;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="game_team",
            joinColumns = @JoinColumn(name = "game_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id",
                    referencedColumnName = "id"))
    private List<Team> teams;

    public Game(User owner) {
        this.teams = new ArrayList<>();
        this.owner = owner;
        this.name = "";
        this.note = "";
    }
}
