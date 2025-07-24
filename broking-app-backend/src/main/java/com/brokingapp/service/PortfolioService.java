package com.brokingapp.service;

import com.brokingapp.model.Holding;
import com.brokingapp.repository.HoldingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    private HoldingRepository holdingRepository;

    public List<Holding> getHoldings(Long userId) {
        return holdingRepository.findByUserId(userId);
    }

    public double getPnL(Long userId) {
        List<Holding> holdings = holdingRepository.findByUserId(userId);
        return holdings.stream().mapToDouble(h -> (h.getCurrentPrice() - h.getAvgPrice()) * h.getQuantity()).sum();
    }
} 