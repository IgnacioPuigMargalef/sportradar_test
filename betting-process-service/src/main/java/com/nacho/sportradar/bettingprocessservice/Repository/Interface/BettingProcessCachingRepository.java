package com.nacho.sportradar.bettingprocessservice.Repository.Interface;

import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;

public interface BettingProcessCachingRepository {
    Bet findBetByClient(String clientId);
    void deleteBetByClient(String clientId);
    void createBet(Bet bet);
}
