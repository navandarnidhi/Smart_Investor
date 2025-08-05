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
    
    // Aadhaar details
    private String aadhaarNumber;
    private String fullName;
    private String dateOfBirth;
    private String gender;
    private String address;
    
    // PAN details
    private String panNumber;
    
    // Bank details
    private String bankAccount;
    
    // Verification details
    private String status; // PENDING, APPROVED, REJECTED, VERIFIED
    private String verificationMethod; // MANUAL, DIGILOCKER
    private String verificationDate;
    
    // Legacy fields for backward compatibility
    private String pan;
    private String aadhaar;
}
