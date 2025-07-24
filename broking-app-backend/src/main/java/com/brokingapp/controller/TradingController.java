package com.brokingapp.controller;

import com.brokingapp.model.Order;
import com.brokingapp.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trading")
public class TradingController {
    @Autowired
    private TradingService tradingService;

    @PostMapping("/order")
    public Order placeOrder(@RequestBody Order order) {
        return tradingService.placeOrder(order);
    }

    @GetMapping("/orders/{userId}")
    public List<Order> getOrders(@PathVariable Long userId) {
        return tradingService.getOrders(userId);
    }
} 