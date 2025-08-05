package com.brokingapp.service;

import com.brokingapp.model.MarketData;
import com.brokingapp.repository.MarketDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class MarketDataService {
    @Autowired
    private MarketDataRepository marketDataRepository;
    
    @Autowired
    private ZerodhaApiService zerodhaApiService;

    public Mono<Map<String, Object>> getIndices() {
        return zerodhaApiService.getQuote("NIFTY")
                .flatMap(niftyData -> zerodhaApiService.getQuote("BANKNIFTY")
                        .map(bankNiftyData -> {
                            Map<String, Object> indices = new HashMap<>();
                            indices.put("nifty", niftyData);
                            indices.put("banknifty", bankNiftyData);
                            return indices;
                        }));
    }

    public Mono<JsonNode> getStockData(String symbol) {
        return zerodhaApiService.getQuote(symbol);
    }

    public Mono<JsonNode> getHistoricalData(String symbol, String fromDate, String toDate, String interval) {
        return zerodhaApiService.getHistoricalData(symbol, fromDate, toDate, interval);
    }

    public Mono<JsonNode> getOptionsChain(String symbol) {
        return zerodhaApiService.getOptionsChain(symbol);
    }

    public Mono<JsonNode> getInstruments(String exchange) {
        return zerodhaApiService.getInstruments(exchange);
    }

    // Fallback method for backward compatibility
    public List<MarketData> getIndicesLegacy() {
        return marketDataRepository.findAll();
    }

    public List<MarketData> getStockDataLegacy(String symbol) {
        return marketDataRepository.findBySymbol(symbol);
    }
} 