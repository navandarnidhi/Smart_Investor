package com.brokingapp.service;

import com.brokingapp.model.Report;
import com.brokingapp.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportsService {
    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getContractNotes(Long userId) {
        return reportRepository.findByUserIdAndType(userId, "CONTRACT_NOTE");
    }

    public List<Report> getTradeBook(Long userId) {
        return reportRepository.findByUserIdAndType(userId, "TRADE_BOOK");
    }

    public List<Report> getPnL(Long userId) {
        return reportRepository.findByUserIdAndType(userId, "PNL");
    }

    public List<Report> getCapitalGains(Long userId) {
        return reportRepository.findByUserIdAndType(userId, "CAPITAL_GAINS");
    }
} 