package com.nacho.sportradar.bettingprocessservice.Model;

import lombok.Builder;

@Builder
public record ClientBetResult(
        String clientId,
        Double totalResult
) {
}
