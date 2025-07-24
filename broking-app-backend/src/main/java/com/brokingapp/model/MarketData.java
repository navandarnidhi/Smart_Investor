package com.brokingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "market_data")
public class MarketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private double lastPrice;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    private LocalDateTime timestamp;
} 