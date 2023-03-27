package com.mays.scorekeeper.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

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
    private String notes;

    @ManyToOne
    private User owner;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="game_team",
            joinColumns = @JoinColumn(name = "game_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id",
                    referencedColumnName = "id"))
    private Set<Team> teams;

    public Game(String name) {
        this.name = name;
    }
}
