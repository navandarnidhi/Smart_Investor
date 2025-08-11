package com.smartinvestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinvestor.model.VerificationCode;

public interface VerificationRepository extends JpaRepository<VerificationCode,Long> {
    VerificationCode findByUserId(Long userId);
}
