package com.DigitalSettings.thermostat.service;

import com.DigitalSettings.thermostat.repository.UnverifiedUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UnverifiedUserCleanupService {

    private final UnverifiedUserRepo unverifiedUserRepository;

    @Scheduled(fixedRate = 5 * 60 * 1000) // Every 5 minutes
    public void deleteOldUnverifiedUsers() throws InterruptedException {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime threeMinutesAgo = currentTime.minusMinutes(10);
        unverifiedUserRepository.deleteByCreatedAtBefore(threeMinutesAgo);
    }

}

