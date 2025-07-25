package com.brokingapp.service;

import com.brokingapp.config.JwtUtils;
import com.brokingapp.model.User;
import com.brokingapp.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final JavaMailSender mailSender;

    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, JavaMailSender mailSender) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.mailSender = mailSender;
    }

    public User registerUser(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Generate OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
        user.setVerified(false);
        User savedUser = userRepo.save(user);
        // Send OTP email
        sendOtpEmail(user.getEmail(), otp);
        return savedUser;
    }

    public boolean verifyOtp(String email, String otp) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) return false;
        User user = optionalUser.get();
        if (user.getOtp() != null && user.getOtp().equals(otp) && user.getOtpExpiry() != null && user.getOtpExpiry().isAfter(LocalDateTime.now())) {
            user.setVerified(true);
            user.setOtp(null);
            user.setOtpExpiry(null);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    public String loginUser(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid email");
        }
        User user = optionalUser.get();
        if (!user.isVerified()) {
            throw new RuntimeException("Email not verified. Please verify your email with OTP.");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtUtils.generateToken(user.getEmail());
    }

    public void sendLoginOtp(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Email not registered");
        }
        User user = optionalUser.get();
        if (!user.isVerified()) {
            throw new RuntimeException("Email not verified");
        }
        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
        userRepo.save(user);
        sendOtpEmail(user.getEmail(), otp);
    }

    public String loginWithOtp(String email, String otp) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Email not registered");
        }
        User user = optionalUser.get();
        if (!user.isVerified()) {
            throw new RuntimeException("Email not verified");
        }
        if (user.getOtp() != null && user.getOtp().equals(otp) && user.getOtpExpiry() != null && user.getOtpExpiry().isAfter(LocalDateTime.now())) {
            user.setOtp(null);
            user.setOtpExpiry(null);
            userRepo.save(user);
            return jwtUtils.generateToken(user.getEmail());
        } else {
            throw new RuntimeException("Invalid or expired OTP");
        }
    }

    private void sendOtpEmail(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Your OTP for SmartInvestor Registration");
            helper.setText("Your OTP is: " + otp + "\nIt is valid for 10 minutes.");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
