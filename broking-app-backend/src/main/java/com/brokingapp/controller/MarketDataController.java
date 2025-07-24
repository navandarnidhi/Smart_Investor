package com.brokingapp.controller;

import com.brokingapp.model.MarketData;
import com.brokingapp.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/marketdata")
public class MarketDataController {
    @Autowired
    private MarketDataService marketDataService;

    @GetMapping("/indices")
    public List<MarketData> getIndices() {
        return marketDataService.getIndices();
    }

    @GetMapping("/stocks/{symbol}")
    public List<MarketData> getStockData(@PathVariable String symbol) {
        return marketDataService.getStockData(symbol);
    }

    @GetMapping("/options/{symbol}")
    public String getOptionsChain(@PathVariable String symbol) {
        return marketDataService.getOptionsChain(symbol);
    }
} 