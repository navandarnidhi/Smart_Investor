package com.brokingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String type; // CONTRACT_NOTE, TRADE_BOOK, PNL, CAPITAL_GAINS
    @Lob
    private String content;
    private LocalDateTime timestamp;
} 