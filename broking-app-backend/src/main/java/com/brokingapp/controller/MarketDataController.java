package com.brokingapp.controller;

import com.brokingapp.model.MarketData;
import com.brokingapp.service.MarketDataService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/marketdata")
@CrossOrigin(origins = "*")
public class MarketDataController {
    @Autowired
    private MarketDataService marketDataService;

    @GetMapping("/indices")
    public Mono<ResponseEntity<Map<String, Object>>> getIndices() {
        return marketDataService.getIndices()
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/stocks/{symbol}")
    public Mono<ResponseEntity<JsonNode>> getStockData(@PathVariable String symbol) {
        return marketDataService.getStockData(symbol)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/historical/{symbol}")
    public Mono<ResponseEntity<JsonNode>> getHistoricalData(
            @PathVariable String symbol,
            @RequestParam String fromDate,
            @RequestParam String toDate,
            @RequestParam(defaultValue = "day") String interval) {
        return marketDataService.getHistoricalData(symbol, fromDate, toDate, interval)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/options/{symbol}")
    public Mono<ResponseEntity<JsonNode>> getOptionsChain(@PathVariable String symbol) {
        return marketDataService.getOptionsChain(symbol)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/instruments/{exchange}")
    public Mono<ResponseEntity<JsonNode>> getInstruments(@PathVariable String exchange) {
        return marketDataService.getInstruments(exchange)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    // Legacy endpoints for backward compatibility
    @GetMapping("/legacy/indices")
    public ResponseEntity<List<MarketData>> getIndicesLegacy() {
        try {
            List<MarketData> indices = marketDataService.getIndicesLegacy();
            return ResponseEntity.ok(indices);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/legacy/stocks/{symbol}")
    public ResponseEntity<List<MarketData>> getStockDataLegacy(@PathVariable String symbol) {
        try {
            List<MarketData> stockData = marketDataService.getStockDataLegacy(symbol);
            return ResponseEntity.ok(stockData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 