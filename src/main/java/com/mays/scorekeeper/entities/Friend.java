package com.mays.scorekeeper.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * A Javabean representing an associated friend object.
 *
 * @author Clayton Mays
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private User friendOf;

    @ManyToMany
    @JoinTable(name="friend_games",
    joinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "game_id",
            referencedColumnName = "id"))
    private List<Game> games;

    @Column(name = "wins")
    private float wins;
    @Column(name = "losses")
    private float losses;

    @Column(name = "note")
    private String note;
}
