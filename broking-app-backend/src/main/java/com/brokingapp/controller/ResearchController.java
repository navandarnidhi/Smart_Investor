package com.brokingapp.controller;

import com.brokingapp.model.ResearchItem;
import com.brokingapp.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/research")
public class ResearchController {
    @Autowired
    private ResearchService researchService;

    @GetMapping("/news")
    public List<ResearchItem> getNews() {
        return researchService.getNews();
    }

    @GetMapping("/charts/{symbol}")
    public List<ResearchItem> getCharts(@PathVariable String symbol) {
        return researchService.getCharts(symbol);
    }

    @GetMapping("/analytics/{symbol}")
    public List<ResearchItem> getAnalytics(@PathVariable String symbol) {
        return researchService.getAnalytics(symbol);
    }
} 