package com.brokingapp.service;

import com.brokingapp.model.ResearchItem;
import com.brokingapp.repository.ResearchItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResearchService {
    @Autowired
    private ResearchItemRepository researchItemRepository;

    public List<ResearchItem> getNews() {
        return researchItemRepository.findByType("NEWS");
    }

    public List<ResearchItem> getCharts(String symbol) {
        return researchItemRepository.findBySymbol(symbol);
    }

    public List<ResearchItem> getAnalytics(String symbol) {
        return researchItemRepository.findBySymbol(symbol);
    }
} 