package com.tracking.service.impl;

import com.tracking.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Password - OTP Code");
        message.setText(
                "Your OTP code is: " + otp +
                        "\n\nThis code is valid for 1 minute."
        );

        mailSender.send(message);
    }
}
