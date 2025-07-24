package com.brokingapp.repository;

import com.brokingapp.model.FundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FundTransactionRepository extends JpaRepository<FundTransaction, Long> {
    List<FundTransaction> findByUserId(Long userId);
} 