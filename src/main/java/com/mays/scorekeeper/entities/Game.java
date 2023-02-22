package com.mays.scorekeeper.entities;

import lombok.*;

import javax.persistence.*;

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

    public Game(String name) {
        this.name = name;
    }
}
