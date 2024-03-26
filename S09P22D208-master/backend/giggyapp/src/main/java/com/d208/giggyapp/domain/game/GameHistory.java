package com.d208.giggyapp.domain.game;

import com.d208.giggyapp.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameHistory {

    @Id @GeneratedValue
    private Long id;

    private int round;
    private int score;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
