package com.nacho.sportradar.bettingprocessservice.Service;

import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for BettingQueueServiceTest class")
@ActiveProfiles("test")
public class BettingQueueServiceTest {

    @InjectMocks
    @Spy
    private BettingQueueService bettingQueueService;

    @Mock
    private ExecutorService executorService;

    @Mock
    private BettingProcessService bettingProcessService;

    @Mock
    private final BlockingQueue<Bet> betQueue = new LinkedBlockingQueue<>();

    @Test
    @DisplayName("Tests for enqueueBet method")
    public void testEnqueueBet() throws InterruptedException {
        Bet bet = new Bet();
        bet.setId(1);

        when(betQueue.take()).thenReturn(bet);

        bettingQueueService.enqueueBet(bet);

        assertEquals(bet.getId(), betQueue.take().getId());
    }
}
