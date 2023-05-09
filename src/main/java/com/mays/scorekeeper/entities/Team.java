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

    private String teamColor;

    public enum BootstrapTableColor {
        PRIMARY("table-primary"),
        SECONDARY("table-secondary"),
        SUCCESS("table-success"),
        DANGER("table-danger"),
        WARNING("table-warning"),
        INFO("table-info"),
        LIGHT("table-light"),
        DARK("table-dark");

        private final String cssClass;

        BootstrapTableColor(String cssClass) {
            this.cssClass = cssClass;
        }

        public String getCssClass() {
            return cssClass;
        }
    }


    public Team(Game game, int teamNumber) {
        this.scores = new ArrayList<>();
        this.name = "";
        this.game = game;
        assignTeamColor(teamNumber);
    }

    public void getTotalScore() {
        for (Integer score : scores) {
            totalScore += score;
        }
    }

    public void assignTeamColor(int teamNumber) {
        switch (teamNumber) {
            case 0:
                this.teamColor = BootstrapTableColor.PRIMARY.getCssClass();
                return;
            case 1:
                this.teamColor = BootstrapTableColor.SECONDARY.getCssClass();
                return;
            case 2:
                this.teamColor = BootstrapTableColor.SUCCESS.getCssClass();
                return;
            case 3:
                this.teamColor = BootstrapTableColor.DANGER.getCssClass();
                return;
            case 4:
                this.teamColor = BootstrapTableColor.WARNING.getCssClass();
                return;
            case 5:
                this.teamColor = BootstrapTableColor.INFO.getCssClass();
                return;
            case 6:
                this.teamColor = BootstrapTableColor.LIGHT.getCssClass();
                return;
            case 7:
                this.teamColor = BootstrapTableColor.DARK.getCssClass();
                return;
            default:
                this.teamColor = "";
                return;
        }
    }
}
