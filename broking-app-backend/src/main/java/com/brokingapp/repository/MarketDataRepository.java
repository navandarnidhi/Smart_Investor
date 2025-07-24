package com.brokingapp.repository;

import com.brokingapp.model.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MarketDataRepository extends JpaRepository<MarketData, Long> {
    List<MarketData> findBySymbol(String symbol);
} 