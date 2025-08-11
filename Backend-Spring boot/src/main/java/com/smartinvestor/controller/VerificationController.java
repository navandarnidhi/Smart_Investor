package com.smartinvestor.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.smartinvestor.exception.UserException;
import com.smartinvestor.model.User;
import com.smartinvestor.model.VerificationCode;
import com.smartinvestor.service.EmailService;
import com.smartinvestor.service.UserService;
import com.smartinvestor.service.VerificationService;

@RestController
public class VerificationController {
    private final VerificationService verificationService;
    private final UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    public VerificationController(VerificationService verificationService, UserService userService) {
        this.verificationService = verificationService;
        this.userService = userService;
    }




}
