package com.brokingapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin_actions")
public class AdminAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long adminId;
    private String actionType; // USER_MANAGEMENT, KYC_APPROVAL, REPORTING, etc.
    private Long targetUserId;
    private String details;
    private LocalDateTime timestamp;
} 