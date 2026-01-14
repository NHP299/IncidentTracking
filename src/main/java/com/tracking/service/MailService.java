package com.tracking.service;

public interface MailService {
    void sendOtp(String username, String otpToken);
}
