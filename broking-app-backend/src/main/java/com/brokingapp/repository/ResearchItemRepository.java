package com.brokingapp.repository;

import com.brokingapp.model.ResearchItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResearchItemRepository extends JpaRepository<ResearchItem, Long> {
    List<ResearchItem> findByType(String type);
    List<ResearchItem> findBySymbol(String symbol);
} 