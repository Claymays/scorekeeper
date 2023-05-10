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

    @ManyToOne
    private Game game;

    @Transient
    private int newScore;

    @Transient
    private int totalScore;

    private String teamColor;

    /**
     * Enumerated fields for team colors
     */
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


    /**
     * Partially qualified class constructor.
     *  Instantiates properties with blank values for form handling
     * @param game Game object to attach team to
     * @param teamNumber number of team currently being created
     */
    public Team(Game game, int teamNumber) {
        this.scores = new ArrayList<>();
        this.name = "Team " + (teamNumber + 1);
        this.game = game;
        this.totalScore = 0;
        assignTeamColor(teamNumber);
    }

    /**
     * Method for determining a teams total score
     */
    public void calculateTotalScore() {
        for (Integer score : scores) {
            totalScore += score;
        }
    }

    /**
     * Method for associating a color with a team
     * @param teamNumber number of the team currently being constructed
     */
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
