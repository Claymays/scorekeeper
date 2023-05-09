package com.mays.scorekeeper.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An entity class representing a team contained in a given game.
 *
 * @author Clayton Mays
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private int id;

    private String name;

    @ElementCollection
    private List<Integer> scores;

    private int newScore;

    private int totalScore;

    @ManyToOne
    private Game game;

    public Team(Game game) {
        this.scores = new ArrayList<>();
        this.name = "";
        this.game = game;
    }

    public void getTotalScore() {
        for (Integer score : scores) {
            totalScore += score;
        }
    }
}
