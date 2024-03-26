package com.d208.giggyapp.repository;

import com.d208.giggyapp.domain.game.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
}
