package com.tracking.controller;

import com.tracking.domain.Role;
import com.tracking.domain.User;
import com.tracking.repository.RoleRepository;
import com.tracking.repository.UserRepository;
import com.tracking.security.JwtService;
import com.tracking.service.MailService;
import com.tracking.service.OtpService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;
    @Autowired private MailService mailService;
    @Autowired private OtpService otpService;

    private final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // >=32 ký tự
    private final long EXPIRATION_MS = 3600000; // 1 giờ


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        String roleName = request.getRoleName() != null ? request.getRoleName() : "USER";

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(role);

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully with role " + roleName);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        if (user.getTokenExpiredAt() != null
                && user.getTokenExpiredAt().after(new Date())) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Yours longed. Please logout and login again.");
        }

        String role = user.getRole() != null
                ? user.getRole().getRoleName()
                : "USER";

        String token = jwtService.generateToken(
                user.getUsername(),
                role
        );

        Date expiredAt = jwtService.extractExpiration(token);

        user.setActiveToken(token);
        user.setTokenExpiredAt(expiredAt);
        userRepository.save(user);

        return ResponseEntity.ok(
                new AuthResponse(token, user.getUsername(), role)
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> req) {
        String username = req.get("username");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = otpService.generateOtp(username);
        mailService.sendOtp(user.getUsername(), otp);

        return ResponseEntity.ok("OTP sent to email");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody Map<String, String> req
    ) {
        String otpToken = req.get("otp");
        String newPassword = req.get("newPassword");

        try {
            Claims claims = jwtService.verifyOtp(otpToken);

            String username = claims.getSubject();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            return ResponseEntity.ok("Password reset successfully");

        } catch (ExpiredJwtException e) {
            return ResponseEntity.badRequest().body("OTP expired");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok("Logged out"); // 👈 KHÔNG trả 401
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.extractAllClaimsIgnoreExpiration(token);
            String username = claims.getSubject();

            userRepository.findByUsername(username).ifPresent(user -> {
                user.setActiveToken(null);
                user.setTokenExpiredAt(null);
                userRepository.save(user);
            });

        } catch (Exception ignored) {
            System.out.println("Invalid token.");
        }

        return ResponseEntity.ok("Logged out successfully");
    }
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthRequest {
    private String username;
    private String password;
    private String roleName;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthResponse {
    private String token;
    private String username;
    private String role;
}

