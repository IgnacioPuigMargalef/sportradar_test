package com.nacho.sportradar.bettingprocessservice.Service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for ShutdownServiceTest class")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ShutdownServiceTest {

    @Mock
    private ExecutorService executorService;

    @InjectMocks
    private ShutdownService shutdownService;

    @Test
    @DisplayName("Test for shutdownSystem() method")
    void shutdownSystem() {
        shutdownService.shutdownSystem();
        assertTrue(true);
    }
}