package com.brokingapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    private String role = "USER"; // default role

    private boolean kycCompleted = false;

    // OTP and email verification fields
    private boolean isVerified = false;
    private String otp;
    private java.time.LocalDateTime otpExpiry;
}
