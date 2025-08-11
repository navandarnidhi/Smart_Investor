package com.smartinvestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinvestor.domain.WithdrawalStatus;
import com.smartinvestor.model.Withdrawal;

import java.util.List;

public interface WithdrawalRepository extends JpaRepository<Withdrawal,Long> {
    List<Withdrawal> findByUserId(Long userId);
}
