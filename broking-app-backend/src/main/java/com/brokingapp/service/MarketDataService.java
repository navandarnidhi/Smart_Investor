package com.brokingapp.service;

import com.brokingapp.model.MarketData;
import com.brokingapp.repository.MarketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarketDataService {
    @Autowired
    private MarketDataRepository marketDataRepository;

    public List<MarketData> getIndices() {
        // Stub: return all market data for now
        return marketDataRepository.findAll();
    }

    public List<MarketData> getStockData(String symbol) {
        return marketDataRepository.findBySymbol(symbol);
    }

    public String getOptionsChain(String symbol) {
        // Stub: implement real options chain logic or API integration
        return "Options chain for " + symbol;
    }
} 