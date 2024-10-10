package com.nacho.sportradar.bettingprocessservice.Controller;

import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;
import com.nacho.sportradar.bettingprocessservice.Service.BettingQueueService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BettingController {

    private BettingQueueService service;

    @PostMapping("/bet")
    public void putBet(@RequestBody Bet bet) {
        service.enqueueBet(bet);
    }
}
