package com.smartinvestor.service;

import com.smartinvestor.domain.VerificationType;
import com.smartinvestor.model.User;
import com.smartinvestor.model.VerificationCode;

public interface VerificationService {
    VerificationCode sendVerificationOTP(User user, VerificationType verificationType);

    VerificationCode findVerificationById(Long id) throws Exception;

    VerificationCode findUsersVerification(User user) throws Exception;

    Boolean VerifyOtp(String opt, VerificationCode verificationCode);

    void deleteVerification(VerificationCode verificationCode);
}
