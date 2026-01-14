package com.tracking.service.impl;

import com.tracking.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private static final long OTP_EXPIRE_SECONDS = 60;
    private static final Random RANDOM = new Random();

    private final RedisTemplate<String, String> redisTemplate;

    private String otpKey(String username) {
        return "OTP:" + username;
    }

    private String lockKey(String username) {
        return "OTP_LOCK:" + username;
    }

    private String generateOtpCode() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }

    @Override
    public String generateOtp(String username) {

        // 🔒 Chống spam: nếu đang lock → không cho gửi
        Boolean locked = redisTemplate.hasKey(lockKey(username));
        if (Boolean.TRUE.equals(locked)) {
            throw new RuntimeException("OTP already sent. Please wait 60 seconds.");
        }

        String otp = generateOtpCode();
        redisTemplate.opsForValue().set(
                otpKey(username),
                otp,
                OTP_EXPIRE_SECONDS,
                TimeUnit.SECONDS
        );

        redisTemplate.opsForValue().set(
                lockKey(username),
                "LOCK",
                OTP_EXPIRE_SECONDS,
                TimeUnit.SECONDS
        );

        return otp;
    }

    @Override
    public void verifyOtp(String username, String otpInput) {

        String key = otpKey(username);
        String savedOtp = redisTemplate.opsForValue().get(key);

        if (savedOtp == null) {
            throw new RuntimeException("OTP expired or not found");
        }

        if (!savedOtp.equals(otpInput)) {
            throw new RuntimeException("OTP incorrect");
        }

        redisTemplate.delete(key);
        redisTemplate.delete(lockKey(username));
    }
}
