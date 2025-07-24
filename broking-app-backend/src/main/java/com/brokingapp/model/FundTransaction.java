package com.brokingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fund_transactions")
public class FundTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String type; // ADD, WITHDRAW
    private double amount;
    private double balance;
    private LocalDateTime timestamp;
} 