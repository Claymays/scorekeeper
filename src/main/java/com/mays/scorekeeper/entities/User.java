package com.mays.scorekeeper.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A javabean for representing users of the application
 *
 * @author Clayton Mays
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    private int id;
    @Column(name = "user_name")
    private String username;
    @JsonIgnore
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_friend",
            joinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<Friend> friendList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_game",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<Game> games;

    /**
     * A partially qualified class constructor.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.games = new ArrayList<>();
        this.friendList = new ArrayList<>();
    }
}

