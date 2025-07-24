package com.brokingapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kyc")
public class Kyc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String pan;
    private String aadhaar;
    private String bankAccount;
    private String status; // e.g., PENDING, APPROVED, REJECTED
}
