package com.erikscepurits.webguesser.dto;

import com.erikscepurits.webguesser.data.Player;

import java.util.List;

public class PlayerLeaderboard {
    private int status;
    private List<Player> playerList;

    // necessary for JUnit testing
    public PlayerLeaderboard() {
    }

    public PlayerLeaderboard(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }
}
