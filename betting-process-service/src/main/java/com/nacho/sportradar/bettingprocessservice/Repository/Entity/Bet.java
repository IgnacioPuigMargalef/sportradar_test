package com.nacho.sportradar.bettingprocessservice.Repository.Entity;

import com.nacho.sportradar.bettingprocessservice.Model.BetStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash
@AllArgsConstructor
@NoArgsConstructor
public class Bet implements Serializable {
    @Id
    private int id;
    private double amount;
    private double odds;
    private String client;
    private String event;
    private String market;
    private String selection;
    private BetStatus status;
}

