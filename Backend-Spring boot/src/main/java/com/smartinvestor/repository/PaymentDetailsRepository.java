package com.smartinvestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinvestor.model.PaymentDetails;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails,Long> {

    PaymentDetails getPaymentDetailsByUserId(Long userId);
}
