package com.brokingapp.service;

import com.brokingapp.model.Kyc;
import com.brokingapp.repository.KycRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class KycService {
    @Autowired
    private KycRepository kycRepository;
    
    @Autowired
    private DigiLockerService digiLockerService;

    public Kyc submitKyc(Kyc kyc) {
        kyc.setStatus("PENDING");
        return kycRepository.save(kyc);
    }

    public Optional<Kyc> getKycStatus(Long userId) {
        return kycRepository.findByUserId(userId);
    }

    public Mono<String> getDigiLockerAuthUrl(String state) {
        return digiLockerService.getAuthorizationUrl(state);
    }

    public Mono<Map<String, Object>> processDigiLockerCallback(String code, String state) {
        return digiLockerService.exchangeCodeForToken(code)
                .flatMap(tokenResponse -> {
                    String accessToken = tokenResponse.get("access_token").asText();
                    return Mono.zip(
                            digiLockerService.getAadhaarDetails(accessToken),
                            digiLockerService.getPanDetails(accessToken)
                    ).map(tuple -> {
                        Map<String, Object> kycData = new HashMap<>();
                        kycData.put("aadhaar", tuple.getT1());
                        kycData.put("pan", tuple.getT2());
                        kycData.put("status", "VERIFIED");
                        return kycData;
                    });
                });
    }

    public Mono<JsonNode> getAadhaarDetails(String accessToken) {
        return digiLockerService.getAadhaarDetails(accessToken);
    }

    public Mono<JsonNode> getPanDetails(String accessToken) {
        return digiLockerService.getPanDetails(accessToken);
    }

    public Mono<JsonNode> getDrivingLicense(String accessToken) {
        return digiLockerService.getDrivingLicense(accessToken);
    }

    public Mono<JsonNode> getPassportDetails(String accessToken) {
        return digiLockerService.getPassportDetails(accessToken);
    }

    public Kyc updateKycWithDigiLockerData(Long userId, Map<String, Object> digiLockerData) {
        Optional<Kyc> existingKyc = kycRepository.findByUserId(userId);
        Kyc kyc;
        
        if (existingKyc.isPresent()) {
            kyc = existingKyc.get();
        } else {
            kyc = new Kyc();
            kyc.setUserId(userId);
        }
        
        // Update KYC with DigiLocker data
        if (digiLockerData.containsKey("aadhaar")) {
            JsonNode aadhaarData = (JsonNode) digiLockerData.get("aadhaar");
            if (aadhaarData.has("data")) {
                JsonNode data = aadhaarData.get("data");
                kyc.setAadhaarNumber(data.get("aadhaar_number").asText());
                kyc.setFullName(data.get("name").asText());
                kyc.setDateOfBirth(data.get("date_of_birth").asText());
                kyc.setGender(data.get("gender").asText());
                kyc.setAddress(data.get("address").asText());
            }
        }
        
        if (digiLockerData.containsKey("pan")) {
            JsonNode panData = (JsonNode) digiLockerData.get("pan");
            if (panData.has("data")) {
                JsonNode data = panData.get("data");
                kyc.setPanNumber(data.get("pan_number").asText());
            }
        }
        
        kyc.setStatus("VERIFIED");
        kyc.setVerificationMethod("DIGILOCKER");
        
        return kycRepository.save(kyc);
    }
}
