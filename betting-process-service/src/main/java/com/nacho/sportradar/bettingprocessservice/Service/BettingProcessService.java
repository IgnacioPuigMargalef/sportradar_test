package com.nacho.sportradar.bettingprocessservice.Service;

import com.nacho.sportradar.bettingprocessservice.Model.BetStatus;
import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;
import com.nacho.sportradar.bettingprocessservice.Repository.Interface.BettingProcessCachingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
@Slf4j
public class BettingProcessService {

    private final Object lock = new Object();
    private final AtomicInteger processedBetsCounter = new AtomicInteger(0);
    private final BettingProcessCachingRepository cacheRepository;
    private final BettingPersistenceWorkerService bettingPersistenceWorkerService;

    public void addBet(final Bet bet) throws InterruptedException{
        log.info("Adding bet {}", bet);
        Thread.sleep(50);
        final BetStatus status = bet.getStatus();

        if(BetStatus.OPEN == status) {
            processOpenBets(bet);
        } else if(BetStatus.LOSER == status | BetStatus.WINNER == status) {
            processClosedBets(bet);
        } else if(BetStatus.VOID == status) {
            processVoidBets(bet);
        }

        synchronized (lock) {
            processedBetsCounter.incrementAndGet();
            lock.notify();
        }

    }

    private void processClosedBets(final Bet bet) {
        log.info("Processing closed bet [{}]", bet);
        final Bet openBet = cacheRepository.findBetByClient(bet.getClient());
        if(openBet == null) {
            bettingPersistenceWorkerService.enqueueBetForPersistence(bet, true);
        } else {
            cacheRepository.deleteBetByClient(openBet.getClient());
            bettingPersistenceWorkerService.enqueueBetForPersistence(bet, false);
        }
    }

    private void processOpenBets(final Bet bet) {
        log.info("Processing open bet [{}]", bet);
        final Bet openBet = cacheRepository.findBetByClient(bet.getClient());
        if(openBet == null) {
            cacheRepository.createBet(bet);
        } else {
            bettingPersistenceWorkerService.enqueueBetForPersistence(bet, true);
        }
    }

    private void processVoidBets(Bet bet) {
        log.info("Processing void bet [{}]", bet);
        final Bet openBet = cacheRepository.findBetByClient(bet.getClient());
        if(openBet != null) {
            cacheRepository.deleteBetByClient(openBet.getClient());
        }
        bettingPersistenceWorkerService.enqueueBetForPersistence(bet, false);
    }

    public int getProcessedBetsCounter() {
        return processedBetsCounter.get();
    }

}
