package com.d208.giggyapp.domain;

import com.d208.giggyapp.domain.game.GameHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String email;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String nickname;

    private String fcmToken;

    private String refreshToken;

    private String accountNumber;

    private String birthday;

    private int targetAmount;

    private int currentAmount;

    private int leftLife;

    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "user")
    private List<GameHistory> gameHistory = new ArrayList<>();

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updateTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void increaseLife(int life) {
        this.leftLife += life;
    }

    public Boolean decreaseLife() {
        if (this.leftLife - 1 >= 0) {
            this.leftLife -= 1;
            return true;
        } else {
            return false;
        }
    }

    public void initLife() {
        this.leftLife = 3;
    }

    public void incraseCurrentAmount(int amount) {
        this.currentAmount += amount;
    }
}
