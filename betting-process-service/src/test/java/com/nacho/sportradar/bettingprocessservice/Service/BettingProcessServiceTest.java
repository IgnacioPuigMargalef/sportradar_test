package com.nacho.sportradar.bettingprocessservice.Service;

import com.nacho.sportradar.bettingprocessservice.Model.BetStatus;
import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;
import com.nacho.sportradar.bettingprocessservice.Repository.Interface.BettingProcessCachingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Tests for BettingQueueServiceTest class")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BettingProcessServiceTest {

    @Mock
    private BettingProcessCachingRepository cacheRepository;

    @Mock
    private BettingPersistenceWorkerService bettingPersistenceWorkerService;

    @InjectMocks
    private BettingProcessService bettingProcessService;

    @Test
    @DisplayName("Tests for testAddOpenBet method")
    void testAddOpenBet() throws InterruptedException {
        Bet bet = new Bet();
        bet.setStatus(BetStatus.OPEN);
        when(cacheRepository.findBetByClient(any())).thenReturn(null);

        bettingProcessService.addBet(bet);

        verify(cacheRepository, times(1)).createBet(bet);
        assertEquals(1, bettingProcessService.getProcessedBetsCounter());
    }

    @Test
    @DisplayName("Tests for testAddWinnerBet_NoOpenBetInCache method")
    void testAddWinnerBet_NoOpenBetInCache() throws InterruptedException {
        Bet bet = new Bet();
        bet.setStatus(BetStatus.WINNER);
        when(cacheRepository.findBetByClient(any())).thenReturn(null);

        bettingProcessService.addBet(bet);

        verify(bettingPersistenceWorkerService, times(1)).enqueueBetForPersistence(bet, true);
        assertEquals(1, bettingProcessService.getProcessedBetsCounter());
    }

    @Test
    @DisplayName("Tests for testAddWinnerBet_WithOpenBetInCache method")
    void testAddWinnerBet_WithOpenBetInCache() throws InterruptedException {
        Bet bet = new Bet();
        bet.setStatus(BetStatus.WINNER);
        Bet openBet = new Bet();
        when(cacheRepository.findBetByClient(any())).thenReturn(openBet);

        bettingProcessService.addBet(bet);

        verify(cacheRepository, times(1)).deleteBetByClient(openBet.getClient());
        verify(bettingPersistenceWorkerService, times(1)).enqueueBetForPersistence(bet, false);
        assertEquals(1, bettingProcessService.getProcessedBetsCounter());
    }

    @Test
    @DisplayName("Tests for testAddVoidBet_WithOpenBetInCache method")
    void testAddVoidBet_WithOpenBetInCache() throws InterruptedException {
        Bet bet = new Bet();
        bet.setStatus(BetStatus.VOID);
        Bet openBet = new Bet();
        when(cacheRepository.findBetByClient(any())).thenReturn(openBet);

        bettingProcessService.addBet(bet);

        verify(cacheRepository, times(1)).deleteBetByClient(openBet.getClient());
        verify(bettingPersistenceWorkerService, times(1)).enqueueBetForPersistence(bet, false);
        assertEquals(1, bettingProcessService.getProcessedBetsCounter());
    }

    @Test
    @DisplayName("Tests for testAddVoidBet_NoOpenBetInCache method")
    void testAddVoidBet_NoOpenBetInCache() throws InterruptedException {
        Bet bet = new Bet();
        bet.setStatus(BetStatus.VOID);
        when(cacheRepository.findBetByClient(any())).thenReturn(null);

        bettingProcessService.addBet(bet);

        verify(cacheRepository, never()).deleteBetByClient(any());
        verify(bettingPersistenceWorkerService, times(1)).enqueueBetForPersistence(bet, false);
        assertEquals(1, bettingProcessService.getProcessedBetsCounter());
    }
}
