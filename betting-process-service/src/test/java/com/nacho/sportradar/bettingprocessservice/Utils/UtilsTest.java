package com.nacho.sportradar.bettingprocessservice.Utils;

import com.nacho.sportradar.bettingprocessservice.Model.BetStatus;
import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;
import com.nacho.sportradar.bettingprocessservice.Repository.Entity.BetSql;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for UtilTest class")
@ActiveProfiles("test") //For evict that BetGenerator runs
class UtilsTest {


    @Test
    @DisplayName("Tests for createParameterSource method")
    void createParameterSource() {
        final String paramName1 = "test";
        final String paramValue1 = "testValue1";
        final String paramName2 = "test1";
        final String paramValue2 = "testValue2";

        final MapSqlParameterSource params = Utils.createParameterSource(paramName1, paramValue1, paramName2, paramValue2);

        Assertions.assertEquals(paramValue1, params.getValue(paramName1));
        Assertions.assertEquals(paramValue2, params.getValue(paramName2));

        Assertions.assertEquals(2, params.getValues().size());
    }

    @Test
    @DisplayName("Tests for mapBetToBetSql method")
    void mapBetToBetSql() {
        Bet bet = new Bet();
        bet.setId(123);
        bet.setAmount(100.0);
        bet.setOdds(2.0);
        bet.setClient("John Doe");
        bet.setEvent("Football Match");
        bet.setMarket("Match Winner");
        bet.setSelection("Team A");
        bet.setStatus(BetStatus.OPEN);

        final String bettingResult = "WINNER";
        final String reviewCheck = "NOT_REVIEWED";

        final BetSql betSql = Utils.mapBetToBetSql(bet, bettingResult, reviewCheck);

        Assertions.assertEquals(bet.getId(), betSql.id());
        Assertions.assertEquals(bet.getAmount(), betSql.amount());
        Assertions.assertEquals(bet.getOdds(), betSql.odds());
        Assertions.assertEquals(bet.getClient(), betSql.client());
        Assertions.assertEquals(bet.getEvent(), betSql.event());
        Assertions.assertEquals(bet.getMarket(), betSql.market());
        Assertions.assertEquals(bet.getSelection(), betSql.selection());
        Assertions.assertEquals(bet.getStatus(), betSql.status());

        Assertions.assertEquals(bettingResult, betSql.bettingResult());
        Assertions.assertEquals(reviewCheck, betSql.reviewCheck());
    }
}