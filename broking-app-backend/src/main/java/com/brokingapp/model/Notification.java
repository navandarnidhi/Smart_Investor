package com.brokingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String type; // TRADE_CONFIRM, PRICE_ALERT, MARGIN_CALL, etc.
    private String message;
    
    @Column(name = "is_read")
    private boolean read;
    
    private LocalDateTime timestamp;
} 