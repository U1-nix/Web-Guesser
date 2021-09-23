package com.erikscepurits.webguesser.dto;

public class PlayerInitialInformation {
    private int status;
    private long sessionId;
    private int triesLeft;

    // necessary for JUnit testing
    public PlayerInitialInformation() {
    }

    public PlayerInitialInformation(int status) {
        this.status = status;
    }

    public PlayerInitialInformation(long id, int triesLeft) {
        status = 1;
        this.sessionId = id;
        this.triesLeft = triesLeft;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public int getTriesLeft() {
        return triesLeft;
    }

    public void setTriesLeft(int triesLeft) {
        this.triesLeft = triesLeft;
    }
}
