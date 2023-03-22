package com.mays.scorekeeper.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * An entity class representing a team contained in a given game.
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

    private String teamName;
    @ElementCollection
    private List<String> members;

    @ElementCollection
    private List<Double> scores;

    @ManyToOne
    private Game game;

}
