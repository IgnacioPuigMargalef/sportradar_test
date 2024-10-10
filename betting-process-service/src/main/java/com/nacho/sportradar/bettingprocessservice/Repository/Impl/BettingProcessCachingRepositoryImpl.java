package com.nacho.sportradar.bettingprocessservice.Repository.Impl;

import com.nacho.sportradar.bettingprocessservice.Repository.Entity.Bet;
import com.nacho.sportradar.bettingprocessservice.Repository.Interface.BettingProcessCachingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
class BettingProcessCachingRepositoryImpl implements BettingProcessCachingRepository {

    private final RedisTemplate<String, Bet> redisTemplate;

    @Override
    public Bet findBetByClient(String clientId) {
        log.info("Finding bet by id: {}", clientId);
        Bet bet = null;
        try {
            bet = redisTemplate.opsForValue().get("client:" + clientId);
            log.info("Bet retrieved from Redis: {}", bet);
        } catch (Exception e) {
            log.error("Error retrieving bet from Redis", e);
        }
        log.info("Found bet: {}", bet);
        return bet;
    }


    @Override
    public void deleteBetByClient(String clientId) {
        log.info("Deleting bet by id: {}", clientId);
        redisTemplate.delete("client:" + clientId);
    }

    @Override
    public void createBet(Bet bet) {
        log.info("Creating bet: {}", bet);
        redisTemplate.opsForValue().set("client:" + bet.getClient(), bet);
    }

}
