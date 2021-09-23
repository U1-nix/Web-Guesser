package com.erikscepurits.webguesser.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByPlayerName(String playerName);
    List<Player> findByGamesPlayedGreaterThanOrderByPlayerSuccessRateDescGamesPlayedAsc(int minimumGamesPlayed);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Player p SET p.wins = :wins WHERE p.playerId = :playerId")
    void wins(@Param(value = "playerId") long playerId, @Param(value = "wins") int wins);

    @Transactional
    @Modifying
    @Query("UPDATE Player p SET p.gamesPlayed = :gamesPlayed WHERE p.playerId = :playerId")
    void updateGamesPlayed(@Param(value = "playerId") long playerId, @Param(value = "gamesPlayed") int gamesPlayed);

    @Transactional
    @Modifying
    @Query("UPDATE Player p SET p.playerSuccessRate = :successRate WHERE p.playerId = :playerId")
    void updateSuccessRate(@Param(value = "playerId") long playerId, @Param(value = "successRate") String successRate);
}
