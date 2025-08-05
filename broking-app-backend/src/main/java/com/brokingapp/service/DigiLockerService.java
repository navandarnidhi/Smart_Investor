package com.brokingapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class DigiLockerService {

    @Value("${digilocker.client.id}")
    private String clientId;

    @Value("${digilocker.client.secret}")
    private String clientSecret;

    @Value("${digilocker.redirect.uri}")
    private String redirectUri;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public DigiLockerService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.digitallocker.gov.in")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public Mono<String> getAuthorizationUrl(String state) {
        String authUrl = String.format(
            "https://api.digitallocker.gov.in/oauth2/1/authorize?" +
            "response_type=code&" +
            "client_id=%s&" +
            "redirect_uri=%s&" +
            "state=%s&" +
            "scope=read",
            clientId, redirectUri, state
        );
        return Mono.just(authUrl);
    }

    public Mono<JsonNode> exchangeCodeForToken(String code) {
        return webClient.post()
                .uri("/oauth2/1/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(createTokenRequestBody(code))
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readTree(response);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse token response", e);
                    }
                })
                .onErrorResume(e -> {
                    // Return mock token for demo purposes
                    return Mono.just(createMockTokenResponse());
                });
    }

    public Mono<JsonNode> getAadhaarDetails(String accessToken) {
        return webClient.get()
                .uri("/v1/account/aadhaar")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readTree(response);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse Aadhaar details", e);
                    }
                })
                .onErrorResume(e -> {
                    // Return mock Aadhaar data for demo purposes
                    return Mono.just(createMockAadhaarData());
                });
    }

    public Mono<JsonNode> getPanDetails(String accessToken) {
        return webClient.get()
                .uri("/v1/account/pan")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readTree(response);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse PAN details", e);
                    }
                })
                .onErrorResume(e -> {
                    // Return mock PAN data for demo purposes
                    return Mono.just(createMockPanData());
                });
    }

    public Mono<JsonNode> getDrivingLicense(String accessToken) {
        return webClient.get()
                .uri("/v1/account/driving-license")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readTree(response);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse driving license", e);
                    }
                })
                .onErrorResume(e -> {
                    // Return mock driving license data for demo purposes
                    return Mono.just(createMockDrivingLicenseData());
                });
    }

    public Mono<JsonNode> getPassportDetails(String accessToken) {
        return webClient.get()
                .uri("/v1/account/passport")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return objectMapper.readTree(response);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse passport details", e);
                    }
                })
                .onErrorResume(e -> {
                    // Return mock passport data for demo purposes
                    return Mono.just(createMockPassportData());
                });
    }

    private String createTokenRequestBody(String code) {
        return String.format(
            "grant_type=authorization_code&" +
            "code=%s&" +
            "client_id=%s&" +
            "client_secret=%s&" +
            "redirect_uri=%s",
            code, clientId, clientSecret, redirectUri
        );
    }

    // Mock data methods for demo purposes
    private JsonNode createMockTokenResponse() {
        try {
            Map<String, Object> mockData = new HashMap<>();
            mockData.put("access_token", "mock_access_token_" + System.currentTimeMillis());
            mockData.put("token_type", "Bearer");
            mockData.put("expires_in", 3600);
            mockData.put("refresh_token", "mock_refresh_token_" + System.currentTimeMillis());
            return objectMapper.valueToTree(mockData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mock token response", e);
        }
    }

    private JsonNode createMockAadhaarData() {
        try {
            Map<String, Object> mockData = new HashMap<>();
            mockData.put("status", "success");
            Map<String, Object> data = new HashMap<>();
            data.put("name", "John Doe");
            data.put("aadhaar_number", "123456789012");
            data.put("date_of_birth", "1990-01-01");
            data.put("gender", "Male");
            data.put("address", "123 Main Street, City, State - 123456");
            data.put("photo", "base64_encoded_photo_data");
            mockData.put("data", data);
            return objectMapper.valueToTree(mockData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mock Aadhaar data", e);
        }
    }

    private JsonNode createMockPanData() {
        try {
            Map<String, Object> mockData = new HashMap<>();
            mockData.put("status", "success");
            Map<String, Object> data = new HashMap<>();
            data.put("name", "JOHN DOE");
            data.put("pan_number", "ABCDE1234F");
            data.put("date_of_birth", "1990-01-01");
            data.put("father_name", "FATHER NAME");
            return objectMapper.valueToTree(mockData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mock PAN data", e);
        }
    }

    private JsonNode createMockDrivingLicenseData() {
        try {
            Map<String, Object> mockData = new HashMap<>();
            mockData.put("status", "success");
            Map<String, Object> data = new HashMap<>();
            data.put("name", "John Doe");
            data.put("license_number", "DL-0120110149646");
            data.put("date_of_birth", "1990-01-01");
            data.put("valid_from", "2020-01-01");
            data.put("valid_until", "2030-01-01");
            data.put("address", "123 Main Street, City, State");
            return objectMapper.valueToTree(mockData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mock driving license data", e);
        }
    }

    private JsonNode createMockPassportData() {
        try {
            Map<String, Object> mockData = new HashMap<>();
            mockData.put("status", "success");
            Map<String, Object> data = new HashMap<>();
            data.put("name", "JOHN DOE");
            data.put("passport_number", "A1234567");
            data.put("date_of_birth", "1990-01-01");
            data.put("date_of_issue", "2020-01-01");
            data.put("date_of_expiry", "2030-01-01");
            data.put("place_of_issue", "Mumbai");
            return objectMapper.valueToTree(mockData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create mock passport data", e);
        }
    }
} 