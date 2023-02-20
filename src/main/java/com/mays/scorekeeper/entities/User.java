package com.mays.scorekeeper.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
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

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("Username: %s \n" +
                "friends: %s \n" +
                "Games: %s", username, friendList.toString(), games.toString());
    }
}

