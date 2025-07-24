package com.brokingapp.controller;

import com.brokingapp.model.Kyc;
import com.brokingapp.service.KycService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kyc")
public class KycController {
    @Autowired
    private KycService kycService;

    @PostMapping("/submit")
    public Kyc submitKyc(@RequestBody Kyc kyc) {
        return kycService.submitKyc(kyc);
    }

    @GetMapping("/status/{userId}")
    public Kyc getKycStatus(@PathVariable Long userId) {
        return kycService.getKycStatus(userId).orElse(null);
    }
}
