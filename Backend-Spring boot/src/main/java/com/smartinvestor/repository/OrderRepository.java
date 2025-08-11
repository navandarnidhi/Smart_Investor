package com.smartinvestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinvestor.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    public List<Order>findByUserId(Long userId);
}
