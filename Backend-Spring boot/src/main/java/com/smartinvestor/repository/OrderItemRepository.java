package com.smartinvestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinvestor.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
