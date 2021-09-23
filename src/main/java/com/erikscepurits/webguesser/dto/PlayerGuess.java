package com.erikscepurits.webguesser.dto;

import javax.validation.constraints.NotNull;

public class PlayerGuess {
    @NotNull
    private long sessionId;
    @NotNull
    private String guess;

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
