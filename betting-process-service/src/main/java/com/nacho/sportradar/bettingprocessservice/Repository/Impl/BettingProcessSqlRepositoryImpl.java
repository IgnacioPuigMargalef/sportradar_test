package com.nacho.sportradar.bettingprocessservice.Repository.Impl;

import com.nacho.sportradar.bettingprocessservice.Exception.GettingTopResultsClientsException;
import com.nacho.sportradar.bettingprocessservice.Exception.GettingTotalBetAmountException;
import com.nacho.sportradar.bettingprocessservice.Exception.GettingTotalBetResultsException;
import com.nacho.sportradar.bettingprocessservice.Model.ClientBetResult;
import com.nacho.sportradar.bettingprocessservice.Repository.Entity.BetSql;
import com.nacho.sportradar.bettingprocessservice.Repository.Interface.BettingProcessSqlRepository;
import com.nacho.sportradar.bettingprocessservice.Repository.Mapper.ClientBetResultRowMapper;
import com.nacho.sportradar.bettingprocessservice.Repository.SqlQueries;
import com.nacho.sportradar.bettingprocessservice.Utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class BettingProcessSqlRepositoryImpl implements BettingProcessSqlRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private JdbcTemplate simpleJdbcTemplate;

    private ClientBetResultRowMapper rowMapper;

    @Override
    public void saveBet(BetSql betSql) {
        log.info("Lets save the bet {}", betSql);
        final MapSqlParameterSource params = Utils.createParameterSource(
                "id", betSql.id(),
                "amount", betSql.amount(),
                "odds", betSql.odds(),
                "client", betSql.client(),
                "event", betSql.event(),
                "market", betSql.market(),
                "selection", betSql.selection(),
                "status", betSql.status().name(),
                "bettingResult", betSql.bettingResult(),
                "reviewCheck", betSql.reviewCheck()
        );

        try {
            jdbcTemplate.update(SqlQueries.CREATE_BET, params);
        } catch (DuplicateKeyException e) {
            log.warn("Bet already exists, you are trying to save a bet closed {}", betSql);
        }
        catch (DataAccessException e) {
            log.error("Error creating bet {}", betSql, e);
        }
    }

    @Override
    public Double getTotalBetAmount() {
        log.info("Lets get the total amount");
        try {
            return simpleJdbcTemplate.queryForObject(SqlQueries.GET_TOTAL_AMOUNT, Double.class);
        } catch (DataAccessException e) {
            log.error("Error querying bets total amount", e);
            throw new GettingTotalBetAmountException();
        }
    }

    @Override
    public Double getTotalBetResult() {
        log.info("Lets get the total bet results");
        try {
            return simpleJdbcTemplate.queryForObject(SqlQueries.GET_TOTAL_RESULTS, Double.class);
        } catch (DataAccessException e) {
            log.error("Error querying bets total results", e);
            throw new GettingTotalBetResultsException();
        }
    }

    @Override
    public List<ClientBetResult> getTop5ClientsHighestProfit() {
        log.info("Lets get the top 5 clients with highest profit");
        try {
            return simpleJdbcTemplate.query(SqlQueries.GET_TOP_5_PROFIT_CLIENTS, rowMapper);
        } catch (DataAccessException e) {
            log.error("Error querying top 5 clients with the highest profit", e);
            throw new GettingTopResultsClientsException();
        }
    }

    @Override
    public List<ClientBetResult> getTop5ClientsLowestProfit() {
        log.info("Lets get the top 5 clients with worst profit");
        try {
            return simpleJdbcTemplate.query(SqlQueries.GET_TOP_5_LOSSES_CLIENTS, rowMapper);
        } catch (DataAccessException e) {
            log.error("Error querying top 5 clients with the lowest profit", e);
            throw new GettingTopResultsClientsException();
        }
    }

}
