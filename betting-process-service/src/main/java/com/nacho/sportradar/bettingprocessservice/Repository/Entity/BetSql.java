package com.nacho.sportradar.bettingprocessservice.Repository.Entity;

import com.nacho.sportradar.bettingprocessservice.Model.BetStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
public record BetSql(
        int id,
        double amount,
        double odds,
        String client,
        String event,
        String market,
        String selection,
        BetStatus status,
        String bettingResult,
        String reviewCheck
) {
}

