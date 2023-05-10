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
    @OneToMany(mappedBy = "owner" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games;

    /**
     * A partially qualified class constructor.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.games = new ArrayList<>();
    }
}

