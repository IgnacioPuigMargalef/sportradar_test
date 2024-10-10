package com.nacho.sportradar.bettingprocessservice.Service;

import com.nacho.sportradar.bettingprocessservice.Model.BetStatus;
import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;
import com.nacho.sportradar.bettingprocessservice.Repository.Entity.BetSql;
import com.nacho.sportradar.bettingprocessservice.Repository.Interface.BettingProcessSqlRepository;
import com.nacho.sportradar.bettingprocessservice.Utils.Constant;
import com.nacho.sportradar.bettingprocessservice.Utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Slf4j
public class BettingPersistenceWorkerService {

    private final BlockingQueue<BetSql> persistenceQueue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService;
    private final BettingProcessSqlRepository bettingProcessSqlRepository;

    @Autowired
    public BettingPersistenceWorkerService(ExecutorService executorService, BettingProcessSqlRepository bettingProcessSqlRepository) {
        this.executorService = executorService;
        this.bettingProcessSqlRepository = bettingProcessSqlRepository;
        startPersistingBets();
    }

    public void enqueueBetForPersistence(Bet bet, boolean needsReview) {
        log.info("Enqueuing bet for persistence: {} (review needed: {})", bet, needsReview);
        double resultBet = 0.0;
        final String review;

        if(needsReview) {
            review = Constant.REVIEW_YES;
        } else {
            review = Constant.REVIEW_NO;
            final BetStatus betStatus = bet.getStatus();
            if(betStatus == BetStatus.WINNER) {
                resultBet = bet.getAmount() * bet.getOdds();
            } else if (betStatus == BetStatus.LOSER) {
                resultBet = -bet.getAmount();
            }
        }

        final BetSql betSql = Utils.mapBetToBetSql(bet, String.valueOf(resultBet), review);

        try {
            persistenceQueue.put(betSql);
        } catch (InterruptedException e) {
            log.error("Error putting bet in persistence queue", e);
            Thread.currentThread().interrupt();
        }
    }

    private void startPersistingBets() {
        log.info("Starting persistence of bets...");
        for (int i = 0; i < 2; i++) {
            executorService.submit(() -> {
                log.info("Starting persistence with thread {}", Thread.currentThread().getName());
                while (true) {
                    try {
                        BetSql bet = persistenceQueue.take();
                        bettingProcessSqlRepository.saveBet(bet);
                        log.info("Persisted bet: {}", bet);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
    }
}
