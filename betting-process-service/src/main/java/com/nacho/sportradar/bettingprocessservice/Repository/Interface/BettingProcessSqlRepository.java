package com.nacho.sportradar.bettingprocessservice.Repository.Interface;

import com.nacho.sportradar.bettingprocessservice.Model.ClientBetResult;
import com.nacho.sportradar.bettingprocessservice.Repository.Entity.BetSql;

import java.util.List;

public interface BettingProcessSqlRepository {

    void saveBet(final BetSql betSql);

    Double getTotalBetAmount();

    Double getTotalBetResult();

    List<ClientBetResult> getTop5ClientsHighestProfit();

    List<ClientBetResult> getTop5ClientsLowestProfit();
}
