package com.d208.giggyapp.repository;

import com.d208.giggyapp.domain.AppAccountHistory;
import com.d208.giggyapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface AppAccountHistoryRepository extends JpaRepository<AppAccountHistory, Long> {
    Optional<AppAccountHistory> findFirstByUserOrderByTransactionDateDesc(User user);
    @Query(value = "select appAccountHistory from AppAccountHistory appAccountHistory " +
            "where appAccountHistory.user = :user AND " +
            "appAccountHistory.transactionDate BETWEEN :startDate AND :endDate")
    List<AppAccountHistory> findByUserAndTransactionDateTimeBetween(
            @Param("user") User user, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Optional<AppAccountHistory> findByUserAndCategory(User user, String category);
}
