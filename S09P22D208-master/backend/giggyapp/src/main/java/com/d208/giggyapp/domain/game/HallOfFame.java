package com.d208.giggyapp.domain.game;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallOfFame {

    @Id @GeneratedValue
    private Long id;

    private int round;
    private int score;
    private String nickname;
    
    private UUID userId;

}
