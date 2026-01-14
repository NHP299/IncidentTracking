package com.tracking.service;

import io.jsonwebtoken.Claims;

public interface OtpService {
    String generateOtp(String username);
    void verifyOtp(String username, String otp);
}

