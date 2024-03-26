package com.d208.giggyapp.domain.begger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallOfBegger {
    @Id
    @GeneratedValue
    private Long id;

    private int season;
    private UUID userId;

}
