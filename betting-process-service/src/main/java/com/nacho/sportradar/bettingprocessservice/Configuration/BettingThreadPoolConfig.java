package com.nacho.sportradar.bettingprocessservice.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class BettingThreadPoolConfig {

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(8); //TODO appl.yml
    }
}
