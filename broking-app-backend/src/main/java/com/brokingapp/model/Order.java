package com.brokingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String symbol;
    private int quantity;
    private double price;
    private String orderType; // MARKET, LIMIT
    private String status; // PENDING, EXECUTED, CANCELLED
    private LocalDateTime timestamp;
} 