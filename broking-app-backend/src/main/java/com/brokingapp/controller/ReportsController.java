package com.brokingapp.controller;

import com.brokingapp.model.Report;
import com.brokingapp.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {
    @Autowired
    private ReportsService reportsService;

    @GetMapping("/contract-notes/{userId}")
    public List<Report> getContractNotes(@PathVariable Long userId) {
        return reportsService.getContractNotes(userId);
    }

    @GetMapping("/trade-book/{userId}")
    public List<Report> getTradeBook(@PathVariable Long userId) {
        return reportsService.getTradeBook(userId);
    }

    @GetMapping("/pnl/{userId}")
    public List<Report> getPnL(@PathVariable Long userId) {
        return reportsService.getPnL(userId);
    }

    @GetMapping("/capital-gains/{userId}")
    public List<Report> getCapitalGains(@PathVariable Long userId) {
        return reportsService.getCapitalGains(userId);
    }
} 