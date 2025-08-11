package com.smartinvestor.request;

import com.smartinvestor.domain.VerificationType;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String sendTo;
    private VerificationType verificationType;
}
