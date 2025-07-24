package com.brokingapp.controller;

import com.brokingapp.model.Holding;
import com.brokingapp.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/holdings/{userId}")
    public List<Holding> getHoldings(@PathVariable Long userId) {
        return portfolioService.getHoldings(userId);
    }

    @GetMapping("/pnl/{userId}")
    public double getPnL(@PathVariable Long userId) {
        return portfolioService.getPnL(userId);
    }
} 