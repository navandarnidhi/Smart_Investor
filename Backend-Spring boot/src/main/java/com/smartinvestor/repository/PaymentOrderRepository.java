package com.smartinvestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinvestor.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Long> {
}
