package com.nacho.sportradar.bettingprocessservice.Service;

import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Slf4j
public class BettingQueueService {

    private final BlockingQueue<Bet> betQueue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService;
    private final BettingProcessService bettingProcessService;

    @Autowired
    public BettingQueueService(ExecutorService executorService, BettingProcessService bettingProcessService) {
        this.executorService = executorService;
        startProcessingBets();
        this.bettingProcessService = bettingProcessService;
    }

    public void enqueueBet(Bet bet) {
        log.info("Enqueuing bet {}", bet);
        try {
            betQueue.put(bet);
        } catch (InterruptedException e) {
            log.error("Error putting a new bet in the queue", e);
            Thread.currentThread().interrupt();
        }
    }

    private void startProcessingBets() {
        log.info("Starting processing of bets...");
        for (int i = 0; i < 8; i++) {
            executorService.submit(() -> {
                log.info("Starting processing of bets with thread {}", Thread.currentThread().getName());
                while (true) {
                    try {
                        Bet bet = betQueue.take();
                        bettingProcessService.addBet(bet);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
    }

}
