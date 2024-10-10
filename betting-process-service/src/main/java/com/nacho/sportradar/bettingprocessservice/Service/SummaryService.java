package com.nacho.sportradar.bettingprocessservice.Service;

import com.nacho.sportradar.bettingprocessservice.Model.ClientBetResult;
import com.nacho.sportradar.bettingprocessservice.Repository.Interface.BettingProcessSqlRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SummaryService {

    private BettingProcessService bettingProcessService;

    private BettingProcessSqlRepository bettingRepository;

    public String getSummary() {
        log.info("Getting summary...");
        final StringBuilder summary = new StringBuilder();

        final Double totalAmount = bettingRepository.getTotalBetAmount();
        final Double totalResult = bettingRepository.getTotalBetResult();
        final List<ClientBetResult> winnerClients = bettingRepository.getTop5ClientsHighestProfit();
        final List<ClientBetResult> loserClients = bettingRepository.getTop5ClientsLowestProfit();
        final int totalBetsProcessed = bettingProcessService.getProcessedBetsCounter();

        summary.append("\n**************************\n")
                .append("      BETTING SUMMARY\n")
                .append("**************************\n\n");

        summary.append("----------------------------------------\n")
                .append("Total number of bets processed: ").append(totalBetsProcessed)
                .append("\n----------------------------------------\n\n")
                .append("----------------------------------------\n")
                .append("Total bet amount: ").append(totalAmount)
                .append("\n----------------------------------------\n\n")
                .append("----------------------------------------\n")
                .append("Total profit or loss: ").append(totalResult)
                .append("\n----------------------------------------\n\n");

        summary.append("----------------------------------------\n")
                .append("Top 5 clients with the highest profits:\n")
                .append("----------------------------------------\n");

        for (int i = 0; i < winnerClients.size(); i++) {
            ClientBetResult client = winnerClients.get(i);
            summary.append(i + 1)
                    .append(". Client: ").append(client.clientId())
                    .append("\n   Total Profit: ").append(client.totalResult())
                    .append("\n\n");
        }

        summary.append("----------------------------------------\n")
                .append("Top 5 clients with the lowest profits:\n")
                .append("----------------------------------------\n");
        for (int i = 0; i < loserClients.size(); i++) {
            ClientBetResult client = loserClients.get(i);
            summary.append(i + 1)
                    .append(". Client: ").append(client.clientId())
                    .append("\n   Total Profit: ").append(client.totalResult())
                    .append("\n\n");
        }

        summary.append("**************************\n")
                .append("      END OF SUMMARY\n")
                .append("**************************");

        log.info(summary.toString());
        return summary.toString();
    }

}
