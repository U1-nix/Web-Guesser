package com.erikscepurits.webguesser.api;

import com.erikscepurits.webguesser.dto.*;
import com.erikscepurits.webguesser.service.GameplayService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class GameplayController {

    private final GameplayService gameplayService;

    public GameplayController(GameplayService gameplayService) {
        this.gameplayService = gameplayService;
    }

    @PostMapping("/start")
    public PlayerInitialInformation init(@Validated @RequestBody PlayerName name, BindingResult result) {
        if (!result.hasErrors()) {
            System.out.println(name.getPlayerName());
            return gameplayService.getInitData(name);
        } else {
            System.out.println(result);
            return new PlayerInitialInformation(0);
        }
    }

    @PostMapping("/game")
	public PlayerGuessResponse getData(@Validated @RequestBody PlayerGuess playerGuess, BindingResult result) {
        if (!result.hasErrors()) {
            return gameplayService.turn(playerGuess);
        } else {
            System.out.println(result);
            return new PlayerGuessResponse(0);
        }
	}

    @PostMapping("/leaderboard")
    public PlayerLeaderboard leaderboard(@Validated @RequestBody MinimumGamesPlayed minimumGamesPlayed, BindingResult result) {
        if (!result.hasErrors()) {
            return gameplayService.getLeaderboard(minimumGamesPlayed);
        } else {
            System.out.println(result);
            return new PlayerLeaderboard(0);
        }
    }
}
