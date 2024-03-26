package com.d208.giggyapp.component;

import com.d208.giggyapp.domain.User;
import com.d208.giggyapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Math.round;

@Component
@RequiredArgsConstructor
public class AutoLifeSetter {

    private final UserRepository userRepository;


    // 소비액에 따른 게임 기회 지급
    @Scheduled(cron = "0 0 23 ? * SUN", zone = "Asia/Seoul")  // "0 * * * * ?"
    @Transactional
    public void lifeForWeek() {
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            int usedAmount = user.getCurrentAmount();
            int targetAmount = user.getTargetAmount();

            int perAmount = round((usedAmount / targetAmount) * 100);

            if (perAmount > 150) {
                user.decreaseLife();
                user.decreaseLife();
            } else if (perAmount > 100) {
                user.decreaseLife();
            } else if (perAmount >= 81) {
                user.initLife();
            } else if (perAmount >= 51) {
                user.increaseLife(1);
            } else {
                user.increaseLife(2);
            }

            int current = user.getCurrentAmount();
            user.updateTargetAmount(0);
            user.incraseCurrentAmount(-current);
        }
    }
}
