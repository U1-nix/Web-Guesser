package com.erikscepurits.webguesser.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    GameSession findBySessionId(long sessionId);

    @Transactional
    @Modifying
    @Query("UPDATE GameSession gs SET gs.tries = :tries WHERE gs.sessionId = :sessionId")
    void makeGuess(@Param(value = "sessionId") long sessionId, @Param(value = "tries") int tries);

    @Transactional
    @Modifying
    @Query("UPDATE GameSession gs SET gs.win = :win WHERE gs.sessionId = :sessionId")
    void win(@Param(value = "sessionId") long sessionId, @Param(value = "win") boolean win);
}
