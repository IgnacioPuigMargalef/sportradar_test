package com.nacho.sportradar.bettingprocessservice.Utils;


import com.nacho.sportradar.bettingprocessservice.Model.BetStatus;
import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;
import com.nacho.sportradar.bettingprocessservice.Repository.Interface.BettingProcessSqlRepository;
import com.nacho.sportradar.bettingprocessservice.Service.BettingQueueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
@Slf4j
@Profile("!test")
public class BetGenerator implements CommandLineRunner {

    private BettingQueueService bettingQueueService;

    @Override
    public void run(String... args) throws Exception {
        final List<Bet> bets = generateRandomBets(100);
        bets.forEach(actualBet -> bettingQueueService.enqueueBet(actualBet));
    }

    public static List<Bet> generateRandomBets(int n) {
        log.info("Generating random bets...");
        final List<Bet> bets = new ArrayList<>();
        final Random random = new Random();

        for (int i = 0; i < n; i++) {
            int id = random.nextInt(70) + 1;
            double amount = random.nextDouble() * 100;
            double odds = 1.0 + random.nextDouble() * 5.0;
            String client = String.valueOf(random.nextInt(61) + 115); //To create more realistic clients
            String event = "Event " + (random.nextInt(50) + 1);
            String market = "Market " + (random.nextInt(5) + 1);
            String selection = "Selection " + (random.nextInt(10) + 1);
            BetStatus status = BetStatus.values()[random.nextInt(BetStatus.values().length)];

            Bet bet = new Bet(id, amount, odds, client, event, market, selection, status);
            bets.add(bet);
        }

        log.info("Generating complete!");
        return bets;
    }
}
