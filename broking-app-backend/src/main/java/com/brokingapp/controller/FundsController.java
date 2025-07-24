package com.brokingapp.controller;

import com.brokingapp.model.FundTransaction;
import com.brokingapp.service.FundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/funds")
public class FundsController {
    @Autowired
    private FundsService fundsService;

    @PostMapping("/add")
    public FundTransaction addFunds(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        double amount = Double.parseDouble(body.get("amount").toString());
        return fundsService.addFunds(userId, amount);
    }

    @PostMapping("/withdraw")
    public FundTransaction withdrawFunds(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        double amount = Double.parseDouble(body.get("amount").toString());
        return fundsService.withdrawFunds(userId, amount);
    }

    @GetMapping("/ledger/{userId}")
    public List<FundTransaction> getLedger(@PathVariable Long userId) {
        return fundsService.getLedger(userId);
    }

    @GetMapping("/balance/{userId}")
    public double getCurrentBalance(@PathVariable Long userId) {
        return fundsService.getCurrentBalance(userId);
    }
} 