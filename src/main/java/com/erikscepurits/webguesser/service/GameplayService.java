package com.erikscepurits.webguesser.service;

import com.erikscepurits.webguesser.data.GameSession;
import com.erikscepurits.webguesser.data.GameSessionRepository;
import com.erikscepurits.webguesser.data.Player;
import com.erikscepurits.webguesser.data.PlayerRepository;
import com.erikscepurits.webguesser.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class GameplayService {
    private final int MAX_TRIES = 8;
    private final GameSessionRepository gameSessionRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public GameplayService(GameSessionRepository gameSessionRepository, PlayerRepository playerRepository) {
        this.gameSessionRepository = gameSessionRepository;
        this.playerRepository = playerRepository;
    }

    public PlayerInitialInformation getInitData(PlayerName playerName) {
        long playerId;
        Player player = playerRepository.findByPlayerName(playerName.getPlayerName());
        if (player == null) {
            player = new Player(playerName.getPlayerName());
            playerId = playerRepository.save(player).getPlayerId();
        } else {
            playerId = player.getPlayerId();
        }

        GameSession gameSession = new GameSession(playerId);

        Random random = new SecureRandom();
        Set<Integer> answerDigits = new LinkedHashSet<>();
        while (answerDigits.size() < 4) {
            int newDigit = random.nextInt(10);
            if (newDigit == 0) {
                if (answerDigits.size() > 0) {
                    answerDigits.add(newDigit);
                }
            } else {
                answerDigits.add(newDigit);
            }
        }

        int answerInt = 0;
        for (Integer digit : answerDigits) {
            answerInt *= 10;
            answerInt += digit;
        }

        gameSession.setAnswer(String.valueOf(answerInt));
        System.out.println(gameSession.getAnswer());

        return new PlayerInitialInformation(gameSessionRepository.save(gameSession).getSessionId(), MAX_TRIES);
    }

    public PlayerGuessResponse turn(PlayerGuess playerGuess) {
        long sessionId = playerGuess.getSessionId();
        GameSession playerGameSession = gameSessionRepository.findBySessionId(sessionId);
        String guess = playerGuess.getGuess();
        String answer = playerGameSession.getAnswer();
        long playerId = playerGameSession.getPlayerId();

        PlayerGuessResponse playerGuessResponse;

        int m = 0;
        int p = 0;
        int tries = playerGameSession.getTries();
        tries++;

        if (!guess.equals(answer)) {
            if (tries < MAX_TRIES) {
                for (int i = 0; i < 4; i++) {
                    char digit = guess.charAt(i);
                    for (int j = 0; j < 4; j++) {
                        if (digit == answer.charAt(j) && i != j) {
                            m++;
                        } else if (digit == answer.charAt(j) && i == j) {
                            p++;
                        }
                    }
                }
            // continue
            gameSessionRepository.makeGuess(sessionId, tries);
            playerGuessResponse = preparePlayerGuessResponse(sessionId, tries, m, p);
            } else {
                // lose / endCondition = false = 0
                endGame(sessionId, playerId, false);
                playerGuessResponse = preparePlayerGuessResponse(sessionId, 0, answer);
            }
        } else {
            // win / endCondition = true = 1
            endGame(sessionId, playerId, true);
            playerGuessResponse = preparePlayerGuessResponse(sessionId, 1, answer);
        }
        return playerGuessResponse;
    }

    private PlayerGuessResponse preparePlayerGuessResponse(long sessionId, int endCondition, String answer) {
        PlayerGuessResponse playerGuessResponseFinal = new PlayerGuessResponse(1);

        playerGuessResponseFinal.setSessionId(sessionId);
        playerGuessResponseFinal.setWin(endCondition);
        playerGuessResponseFinal.setAnswer(answer);

        return playerGuessResponseFinal;
    }

    private PlayerGuessResponse preparePlayerGuessResponse(long sessionId, int tries, int m, int p) {
        PlayerGuessResponse playerGuessResponseContinue = new PlayerGuessResponse(1);

        playerGuessResponseContinue.setSessionId(sessionId);
        playerGuessResponseContinue.setTries(tries);
        playerGuessResponseContinue.setWin(-1);
        playerGuessResponseContinue.setM(m);
        playerGuessResponseContinue.setP(p);
        playerGuessResponseContinue.setTriesLeft(MAX_TRIES - tries);

        return playerGuessResponseContinue;
    }

    private void endGame(long sessionsId, long playerId, boolean endCondition) {
        gameSessionRepository.win(sessionsId, endCondition);

        int gamesPlayed = playerRepository.getById(playerId).getGamesPlayed();
        playerRepository.updateGamesPlayed(playerId, ++gamesPlayed);

        if(endCondition) {
            int wins = playerRepository.getById(playerId).getWins();
            playerRepository.wins(playerId, ++wins);
        }

        if (gamesPlayed > 0) {
            int wins = playerRepository.getById(playerId).getWins();
            float successRate = ( (float) wins) / gamesPlayed;
            playerRepository.updateSuccessRate(playerId, String.valueOf(successRate));
        }
    }

    public PlayerLeaderboard getLeaderboard(MinimumGamesPlayed minimumGamesPlayed) {
        PlayerLeaderboard playerLeaderboard = new PlayerLeaderboard(1);
        playerLeaderboard.setPlayerList(playerRepository.findByGamesPlayedGreaterThanOrderByPlayerSuccessRateDescGamesPlayedAsc(minimumGamesPlayed.getMinimumGamesPlayed()-1));
        return playerLeaderboard;
    }
}
