package com.nacho.sportradar.bettingprocessservice.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class ShutdownService {

    private final ExecutorService executorService;

    public void shutdownSystem() {
        log.info("Shutting down system...");
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                log.warn("Forcing shutdown as some threads did not finish in time...");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Shutdown was interrupted, forcing shutdown...");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("Shutting down complete...");
    }
}
