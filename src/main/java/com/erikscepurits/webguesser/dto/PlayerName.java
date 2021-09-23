package com.erikscepurits.webguesser.dto;

import javax.validation.constraints.NotNull;

public class PlayerName {
    @NotNull
    private String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
