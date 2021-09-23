package com.erikscepurits.webguesser.data;

import javax.persistence.*;

@Entity
public class GameSession {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long sessionId;
    private long playerId;
    private boolean win;
    private int tries;
    private String answer;

    public GameSession() {
    }

    public GameSession(long playerId) {
        this.playerId = playerId;
        tries = 0;
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
