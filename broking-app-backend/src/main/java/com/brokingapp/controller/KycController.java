package com.brokingapp.controller;

import com.brokingapp.model.Kyc;
import com.brokingapp.service.KycService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/kyc")
@CrossOrigin(origins = "*")
public class KycController {
    @Autowired
    private KycService kycService;

    @PostMapping("/submit")
    public ResponseEntity<Kyc> submitKyc(@RequestBody Kyc kyc) {
        try {
            Kyc savedKyc = kycService.submitKyc(kyc);
            return ResponseEntity.ok(savedKyc);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/status/{userId}")
    public ResponseEntity<Kyc> getKycStatus(@PathVariable Long userId) {
        return kycService.getKycStatus(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DigiLocker Integration Endpoints
    @GetMapping("/digilocker/auth")
    public Mono<ResponseEntity<String>> getDigiLockerAuthUrl(@RequestParam String state) {
        return kycService.getDigiLockerAuthUrl(state)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/digilocker/callback")
    public Mono<ResponseEntity<Map<String, Object>>> processDigiLockerCallback(
            @RequestParam String code,
            @RequestParam String state) {
        return kycService.processDigiLockerCallback(code, state)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/digilocker/update/{userId}")
    public Mono<ResponseEntity<Kyc>> updateKycWithDigiLocker(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> digiLockerData) {
        return Mono.fromCallable(() -> kycService.updateKycWithDigiLockerData(userId, digiLockerData))
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/digilocker/aadhaar")
    public Mono<ResponseEntity<JsonNode>> getAadhaarDetails(@RequestParam String accessToken) {
        return kycService.getAadhaarDetails(accessToken)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/digilocker/pan")
    public Mono<ResponseEntity<JsonNode>> getPanDetails(@RequestParam String accessToken) {
        return kycService.getPanDetails(accessToken)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/digilocker/driving-license")
    public Mono<ResponseEntity<JsonNode>> getDrivingLicense(@RequestParam String accessToken) {
        return kycService.getDrivingLicense(accessToken)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/digilocker/passport")
    public Mono<ResponseEntity<JsonNode>> getPassportDetails(@RequestParam String accessToken) {
        return kycService.getPassportDetails(accessToken)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }
}
