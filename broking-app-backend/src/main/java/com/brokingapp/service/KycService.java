package com.brokingapp.service;

import com.brokingapp.model.Kyc;
import com.brokingapp.repository.KycRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class KycService {
    @Autowired
    private KycRepository kycRepository;

    public Kyc submitKyc(Kyc kyc) {
        kyc.setStatus("PENDING");
        return kycRepository.save(kyc);
    }

    public Optional<Kyc> getKycStatus(Long userId) {
        return kycRepository.findByUserId(userId);
    }
}
