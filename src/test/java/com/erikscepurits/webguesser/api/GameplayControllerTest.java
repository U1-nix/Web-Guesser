package com.erikscepurits.webguesser.api;

import com.erikscepurits.webguesser.WebGuesserApplication;
import com.erikscepurits.webguesser.data.GameSessionRepository;
import com.erikscepurits.webguesser.data.Player;
import com.erikscepurits.webguesser.data.PlayerRepository;
import com.erikscepurits.webguesser.dto.*;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.util.AssertionErrors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = WebGuesserApplication.class)
@AutoConfigureMockMvc
class GameplayControllerTest {
    @Autowired
    private final GameSessionRepository gameSessionRepository;
    @Autowired
    private final PlayerRepository playerRepository;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    GameplayControllerTest(GameSessionRepository gameSessionRepository, PlayerRepository playerRepository) {
        this.gameSessionRepository = gameSessionRepository;
        this.playerRepository = playerRepository;
    }

    @Test
    void testNewPlayerInitialization() {
        System.out.println();
        try {
            PlayerName playerName = new PlayerName();
            playerName.setPlayerName("Eric");

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
            String requestJson = objectWriter.writeValueAsString(playerName);

            MvcResult mvcResult = this.mockMvc.perform(post("/start").contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)).andReturn();

            String contentAsString = mvcResult.getResponse().getContentAsString();
            PlayerInitialInformation playerInitialInformation = objectMapper.readValue(contentAsString, PlayerInitialInformation.class);

            assertNotNull("Gameplay session check (normal case)", playerInitialInformation.getSessionId());

            PlayerName emptyPlayerName = new PlayerName();
            requestJson = objectWriter.writeValueAsString(emptyPlayerName);

            mvcResult = this.mockMvc.perform(post("/start").contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)).andReturn();

            contentAsString = mvcResult.getResponse().getContentAsString();
            PlayerInitialInformation playerInitialInformationEmpty = objectMapper.readValue(contentAsString, PlayerInitialInformation.class);

            assertEquals("Gameplay session check (empty name case)", 0, playerInitialInformationEmpty.getStatus());

        } catch (JacksonException e) {
            assertEquals("Jackson matching", true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    @Test
    void testGameplayLose() {
        testGameplay(false);
    }

    @Test
    void testGameplayWin() {
        testGameplay(true);
    }

    @Test
    void testLeaderboard() {
        Player player = new Player("Eric");
        player.setGamesPlayed(2);
        player.setWins(1);
        player.setPlayerSuccessRate("0.5");
        playerRepository.save(player);

        Player player1 = new Player("Martin");
        player1.setGamesPlayed(1);
        player.setWins(1);
        player.setPlayerSuccessRate("1.0");
        playerRepository.save(player1);

        try {
            MinimumGamesPlayed minimumGamesPlayed = new MinimumGamesPlayed();
            minimumGamesPlayed.setMinimumGamesPlayed(2);

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

            String requestJson = objectWriter.writeValueAsString(minimumGamesPlayed);

            MvcResult mvcResult = this.mockMvc.perform(post("/leaderboard").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andReturn();

            String contentAsString = mvcResult.getResponse().getContentAsString();
            PlayerLeaderboard playerLeaderboard = objectMapper.readValue(contentAsString, PlayerLeaderboard.class);
            List<Player> playerList = playerLeaderboard.getPlayerList();

            assertEquals("Player count (minimal games input check)", 1, playerList.size());
            assertEquals("Player name (minimal games input check)", "Eric", playerList.get(0).getPlayerName());


            minimumGamesPlayed = new MinimumGamesPlayed();
            minimumGamesPlayed.setMinimumGamesPlayed(1);

            requestJson = objectWriter.writeValueAsString(minimumGamesPlayed);

            mvcResult = this.mockMvc.perform(post("/leaderboard").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andReturn();

            contentAsString = mvcResult.getResponse().getContentAsString();
            playerLeaderboard = objectMapper.readValue(contentAsString, PlayerLeaderboard.class);
            playerList = playerLeaderboard.getPlayerList();

            String[] expectedPlayerNames = {"Eric", "Martin"};
            String[] actualPlayerNames = new String[2];
            for (int i = 0; i < 2; i++) {
                actualPlayerNames[i] = playerList.get(i).getPlayerName();
            }

            assertEquals("Player count (2)", 2, playerList.size());
            assertArrayEquals(expectedPlayerNames, actualPlayerNames);
        } catch (JacksonException e) {
            assertEquals("Jackson matching", true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //void

    void testGameplay(boolean condition) {
        try {
            PlayerName playerName = new PlayerName();
            playerName.setPlayerName("Eric");

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
            String requestJson = objectWriter.writeValueAsString(playerName);

            MvcResult mvcResult = this.mockMvc.perform(post("/start").contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)).andReturn();

            String contentAsString = mvcResult.getResponse().getContentAsString();
            PlayerInitialInformation playerInitialInformation = objectMapper.readValue(contentAsString, PlayerInitialInformation.class);
            long sessionId = playerInitialInformation.getSessionId();

            int winTry = -1;
            if (condition) {
                winTry = ThreadLocalRandom.current().nextInt(1, playerInitialInformation.getTriesLeft());
            }

            for(int i = 0; i <= playerInitialInformation.getTriesLeft(); i++) {
                PlayerGuess playerGuess = new PlayerGuess();
                playerGuess.setSessionId(sessionId);
                int randomInt;
                do {
                    randomInt = ThreadLocalRandom.current().nextInt(1000, 10000);
                    playerGuess.setGuess(String.valueOf(randomInt));
                } while (randomInt == Integer.parseInt(gameSessionRepository.findBySessionId(sessionId).getAnswer()));
                if (i == winTry) {
                    playerGuess.setGuess(gameSessionRepository.findBySessionId(sessionId).getAnswer());
                }
                requestJson = objectWriter.writeValueAsString(playerGuess);
                mvcResult = this.mockMvc.perform(post("/game").contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)).andReturn();

                contentAsString = mvcResult.getResponse().getContentAsString();
                PlayerGuessResponse playerGuessResponse = objectMapper.readValue(contentAsString, PlayerGuessResponse.class);
                if (playerGuessResponse.getWin() == 0) {
                    assertNotEquals("Lose condition (answer check)", gameSessionRepository.findBySessionId(sessionId).getAnswer(), playerGuess.getGuess());
                    break;
                } else if (playerGuessResponse.getWin() == 1) {
                    assertEquals("Win condition (answer check)", gameSessionRepository.findBySessionId(sessionId).getAnswer(), playerGuess.getGuess());
                    break;
                }
            }
            playerRepository.deleteById(playerRepository.findByPlayerName("Eric").getPlayerId());
        } catch (JacksonException e) {
            assertEquals("Jackson matching", true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}