package com.nacho.sportradar.bettingprocessservice.Controller;

import com.nacho.sportradar.bettingprocessservice.Service.ShutdownService;
import com.nacho.sportradar.bettingprocessservice.Service.SummaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ShutdownController {

    private ShutdownService shutdownService;
    private SummaryService summaryService;

    @PostMapping("/shutdown")
    public String shutdown() {
        shutdownService.shutdownSystem();
        return summaryService.getSummary();
    }
}
