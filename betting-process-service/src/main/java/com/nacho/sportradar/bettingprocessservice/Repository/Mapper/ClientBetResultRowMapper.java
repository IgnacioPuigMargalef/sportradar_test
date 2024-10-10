package com.nacho.sportradar.bettingprocessservice.Repository.Mapper;

import com.nacho.sportradar.bettingprocessservice.Model.ClientBetResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class ClientBetResultRowMapper implements RowMapper<ClientBetResult> {

    @Override
    public ClientBetResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClientBetResult.builder()
                .clientId(rs.getString("client"))
                .totalResult(rs.getDouble("totalProfit"))
                .build();
    }
}
