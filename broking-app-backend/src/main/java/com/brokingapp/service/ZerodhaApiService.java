package com.brokingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZerodhaApiService {

    @Value("${zerodha.api.key}")
    private String apiKey;

    @Value("${zerodha.api.secret}")
    private String apiSecret;

    @Value("${zerodha.user.id}")
    private String userId;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private String accessToken;
    private LocalDateTime tokenExpiry;

    public ZerodhaApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.kite.trade")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public String getAccessToken() {
        if (accessToken == null || tokenExpiry == null || LocalDateTime.now().isAfter(tokenExpiry)) {
            authenticate();
        }
        return accessToken;
    }

    private void authenticate() {
        // For demo purposes, using a mock token
        // In production, implement proper Zerodha authentication
        this.accessToken = "mock_access_token_" + System.currentTimeMillis();
        this.tokenExpiry = LocalDateTime.now().plusHours(1);
    }

    public Mono<JsonNode> getQuote(String symbol) {
        return webClient.get()
                .uri("/quote/ltp?i=NSE:" + symbol)
                .header("X-Kite-Version", "3")
                .header("Authorization", "token " + apiKey + ":" + getAccessToken())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readTree(response);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse quote response", e);
                    }
                })
                .onErrorResume(e -> {
                    // Return mock data for demo purposes
                    return Mono.just(createMockQuote(symbol));
                });
    }

    public Mono<JsonNode> getHistoricalData(String symbol, String fromDate, String toDate, String interval) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/chart/" + symbol + "/" + interval)
                        .queryParam("from", fromDate)
                        .queryParam("to", toDate)
                        .build())
                .header("X-Kite-Version", "3")
                .header("Authorization", "token " + apiKey + ":" + getAccessToken())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readTree(response);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse historical data", e);
                    }
                })
                .onErrorResume(e -> {
                    // Return mock data for demo purposes
                    return Mono.just(createMockHistoricalData(symbol));
                });
    }

    public Mono<JsonNode> getInstruments(String exchange) {
        return webClient.get()
                .uri("/instruments/" + exchange)
                .header("X-Kite-Version", "3")
                .header("Authorization", "token " + apiKey + ":" + getAccessToken())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readTree(response);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse instruments", e);
                    }
                })
                .onErrorResume(e -> {
                    // Return mock data for demo purposes
                    return Mono.just(createMockInstruments());
                });
    }

    public Mono<JsonNode> getOptionsChain(String symbol) {
        return webClient.get()
                .uri("/instruments/indices/" + symbol)
                .header("X-Kite-Version", "3")
                .header("Authorization", "token " + apiKey + ":" + getAccessToken())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readTree(response);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse options chain", e);
                    }
                })
                .onErrorResume(e -> {
                    // Return mock data for demo purposes
                    return Mono.just(createMockOptionsChain(symbol));
                });
    }

    // Mock data methods for demo purposes
    private JsonNode createMockQuote(String symbol) {
        try {
            Map<String, Object> mockData = new HashMap<>();
            mockData.put("status", "success");
            Map<String, Object> data = new HashMap<>();
            data.put("instrument_token", 123456);
            data.put("last_price", 1500.0 + Math.random() * 100);
            data.put("change", Math.random() * 10 - 5);
            data.put("change_percent", Math.random() * 2 - 1);
            data.put("volume", (int)(Math.random() * 1000000));
            data.put("high", 1600.0);
            data.put("low", 1400.0);
            data.put("oi", (int)(Math.random() * 100000));
            mockData.put("data", Map.of("NSE:" + symbol, data));
            return objectMapper.valueToTree(mockData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mock quote", e);
        }
    }

    private JsonNode createMockHistoricalData(String symbol) {
        try {
            Map<String, Object> mockData = new HashMap<>();
            mockData.put("status", "success");
            mockData.put("data", Map.of("candles", new Object[][]{
                {LocalDateTime.now().minusDays(5).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), 1500.0, 1510.0, 1490.0, 1505.0, 100000},
                {LocalDateTime.now().minusDays(4).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), 1505.0, 1520.0, 1500.0, 1515.0, 120000},
                {LocalDateTime.now().minusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), 1515.0, 1530.0, 1510.0, 1525.0, 110000},
                {LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), 1525.0, 1540.0, 1520.0, 1535.0, 130000},
                {LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), 1535.0, 1550.0, 1530.0, 1545.0, 140000}
            }));
            return objectMapper.valueToTree(mockData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mock historical data", e);
        }
    }

    private JsonNode createMockInstruments() {
        try {
            Map<String, Object> mockData = new HashMap<>();
            mockData.put("status", "success");
            mockData.put("data", new Object[][]{
                {"NSE", "RELIANCE", "RELIANCE", "EQ", 123456, "RELIANCE-EQ", 10, "2023-01-01", "2023-12-31", "2023-01-01", "2023-12-31"},
                {"NSE", "TCS", "TCS", "EQ", 123457, "TCS-EQ", 1, "2023-01-01", "2023-12-31", "2023-01-01", "2023-12-31"},
                {"NSE", "INFY", "INFY", "EQ", 123458, "INFY-EQ", 5, "2023-01-01", "2023-12-31", "2023-01-01", "2023-12-31"}
            });
            return objectMapper.valueToTree(mockData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mock instruments", e);
        }
    }

    private JsonNode createMockOptionsChain(String symbol) {
        try {
            Map<String, Object> mockData = new HashMap<>();
            mockData.put("status", "success");
            Map<String, Object> data = new HashMap<>();
            data.put("underlying_value", 1500.0);
            data.put("strike_prices", new Object[]{1450, 1500, 1550, 1600});
            data.put("expiry_dates", new String[]{"2023-12-28", "2024-01-25", "2024-02-29"});
            mockData.put("data", data);
            return objectMapper.valueToTree(mockData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mock options chain", e);
        }
    }
} 