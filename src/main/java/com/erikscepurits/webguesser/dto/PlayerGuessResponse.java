package com.erikscepurits.webguesser.dto;

public class PlayerGuessResponse {
    private int status;
    private long sessionId;
    private int win;
    private int tries;
    private int m;
    private int p;
    private String answer;
    private int triesLeft;

    public PlayerGuessResponse() {
    }

    public PlayerGuessResponse(int status) {
        this.status = status;
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

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        // 1 - win
        // 0 - lose
        // -1 - continue
        this.win = win;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTriesLeft() {
        return triesLeft;
    }

    public void setTriesLeft(int triesLeft) {
        this.triesLeft = triesLeft;
    }
}
