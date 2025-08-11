package com.smartinvestor.model;

import com.smartinvestor.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {

    private boolean isEnabled = false;
    private VerificationType sendTo;
}
