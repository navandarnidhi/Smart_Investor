package com.smartinvestor.service;

import com.smartinvestor.domain.VerificationType;
import com.smartinvestor.model.ForgotPasswordToken;
import com.smartinvestor.model.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id, String otp,
                                    VerificationType verificationType,String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);

    boolean verifyToken(ForgotPasswordToken token,String otp);
}
