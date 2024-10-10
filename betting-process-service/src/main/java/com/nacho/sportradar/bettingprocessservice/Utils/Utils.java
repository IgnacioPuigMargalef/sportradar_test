package com.nacho.sportradar.bettingprocessservice.Utils;

import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;
import com.nacho.sportradar.bettingprocessservice.Repository.Entity.BetSql;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class Utils {

    public static MapSqlParameterSource createParameterSource(Object... params) {
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        for (int i = 0; i < params.length; i += 2) {
            String name = (String) params[i];
            Object value = params[i + 1];
            parameterSource.addValue(name, value);
        }

        return parameterSource;
    }

    public static BetSql mapBetToBetSql(Bet bet, String bettingResult, String reviewCheck) {
        return new BetSql(
                bet.getId(),
                bet.getAmount(),
                bet.getOdds(),
                bet.getClient(),
                bet.getEvent(),
                bet.getMarket(),
                bet.getSelection(),
                bet.getStatus(),
                bettingResult,
                reviewCheck
        );
    }

}
