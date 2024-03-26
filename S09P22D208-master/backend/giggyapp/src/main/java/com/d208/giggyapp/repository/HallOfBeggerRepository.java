package com.d208.giggyapp.repository;

import com.d208.giggyapp.domain.begger.HallOfBegger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallOfBeggerRepository extends JpaRepository<HallOfBegger, Long> {
}
