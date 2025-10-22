package com.tracking.controller;

import com.tracking.domain.Role;
import com.tracking.domain.User;
import com.tracking.repository.RoleRepository;
import com.tracking.repository.UserRepository;
import com.tracking.security.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private CustomUserDetailsService userDetailsService;
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private RoleRepository roleRepository;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Role defaultRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(defaultRole);

        userRepository.save(newUser);
        return ResponseEntity.ok(" User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("USER"); // default nếu không có role nào

        return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername(), role));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {
        Optional<User> userOpt = userRepository.findByUsername(req.getUsername());

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(" User not found");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(" Password updated successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(" Logged out successfully (client should remove token)");
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthRequest {
    private String username;
    private String password;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthResponse {
    private String token;
    private String username;
    private String role;
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class ResetPasswordRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}
