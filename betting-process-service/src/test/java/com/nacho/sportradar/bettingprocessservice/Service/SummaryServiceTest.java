package com.nacho.sportradar.bettingprocessservice.Service;

import com.nacho.sportradar.bettingprocessservice.Model.ClientBetResult;
import com.nacho.sportradar.bettingprocessservice.Repository.Interface.BettingProcessSqlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for SummaryServiceTest class")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class SummaryServiceTest {

    @InjectMocks
    private SummaryService summaryService;

    @Mock
    private BettingProcessService bettingProcessService;

    @Mock
    private BettingProcessSqlRepository bettingRepository;

    @Test
    @DisplayName("Tests for testGetSummary_NotEmpty method")
    public void testGetSummary_NotEmpty() {
        when(bettingRepository.getTotalBetAmount()).thenReturn(1000.0);
        when(bettingRepository.getTotalBetResult()).thenReturn(200.0);
        when(bettingRepository.getTop5ClientsHighestProfit()).thenReturn(Arrays.asList(
                new ClientBetResult("client1", 500.0),
                new ClientBetResult("client2", 400.0),
                new ClientBetResult("client3", 300.0),
                new ClientBetResult("client4", 200.0),
                new ClientBetResult("client5", 100.0)
        ));
        when(bettingRepository.getTop5ClientsLowestProfit()).thenReturn(Arrays.asList(
                new ClientBetResult("client6", -100.0),
                new ClientBetResult("client7", -200.0),
                new ClientBetResult("client8", -300.0),
                new ClientBetResult("client9", -400.0),
                new ClientBetResult("client10", -500.0)
        ));

        when(bettingProcessService.getProcessedBetsCounter()).thenReturn(15);

        String summary = summaryService.getSummary();
        assertFalse(summary.isEmpty(), "The summary should not be empty!");
    }
}
