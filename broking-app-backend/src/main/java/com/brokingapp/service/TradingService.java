package com.brokingapp.service;

import com.brokingapp.model.Order;
import com.brokingapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradingService {
    @Autowired
    private OrderRepository orderRepository;

    public Order placeOrder(Order order) {
        order.setStatus("PENDING");
        order.setTimestamp(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> getOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
} 