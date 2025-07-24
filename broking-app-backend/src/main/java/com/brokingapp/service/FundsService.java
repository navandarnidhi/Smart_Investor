package com.brokingapp.service;

import com.brokingapp.model.FundTransaction;
import com.brokingapp.repository.FundTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FundsService {
    @Autowired
    private FundTransactionRepository fundTransactionRepository;

    public FundTransaction addFunds(Long userId, double amount) {
        double balance = getCurrentBalance(userId) + amount;
        FundTransaction tx = new FundTransaction(null, userId, "ADD", amount, balance, LocalDateTime.now());
        return fundTransactionRepository.save(tx);
    }

    public FundTransaction withdrawFunds(Long userId, double amount) {
        double balance = getCurrentBalance(userId) - amount;
        FundTransaction tx = new FundTransaction(null, userId, "WITHDRAW", amount, balance, LocalDateTime.now());
        return fundTransactionRepository.save(tx);
    }

    public List<FundTransaction> getLedger(Long userId) {
        return fundTransactionRepository.findByUserId(userId);
    }

    public double getCurrentBalance(Long userId) {
        List<FundTransaction> ledger = fundTransactionRepository.findByUserId(userId);
        return ledger.isEmpty() ? 0 : ledger.get(ledger.size() - 1).getBalance();
    }
} 