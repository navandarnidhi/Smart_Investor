package com.smartinvestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinvestor.model.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP,String> {

    TwoFactorOTP findByUserId(Long userId);
}
