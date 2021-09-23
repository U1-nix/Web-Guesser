package com.erikscepurits.webguesser.dto;

import javax.validation.constraints.NotNull;

public class MinimumGamesPlayed {
    @NotNull
    private int minimumGamesPlayed;

    public int getMinimumGamesPlayed() {
        return minimumGamesPlayed;
    }

    public void setMinimumGamesPlayed(int minimumGamesPlayed) {
        this.minimumGamesPlayed = minimumGamesPlayed;
    }
}
