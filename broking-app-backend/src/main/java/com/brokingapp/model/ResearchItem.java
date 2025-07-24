package com.brokingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "research_items")
public class ResearchItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type; // NEWS, CHART, ANALYTICS
    @Lob
    private String content;
    private String symbol;
    private LocalDateTime timestamp;
} 