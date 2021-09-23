package com.erikscepurits.webguesser.data;

import javax.persistence.*;

@Table
@Entity
public class Player {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long playerId;
    private String playerName;
    private String playerSuccessRate;
    private int wins;
    private int gamesPlayed;

    public Player() {
    }

    public Player(String playerName) {
        this.playerName = playerName;
        this.playerSuccessRate = "0";
        this.wins = 0;
        this.gamesPlayed = 0;
    }

    public long getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerSuccessRate() {
        return playerSuccessRate;
    }

    public void setPlayerSuccessRate(String playerSuccess) {
        this.playerSuccessRate = playerSuccess;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int totalTries) {
        this.gamesPlayed = totalTries;
    }
}
