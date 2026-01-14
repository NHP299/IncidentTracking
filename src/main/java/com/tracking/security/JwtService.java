package com.tracking.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "your-256-bit-secret-your-256-bit-secret";

    private static final long LOGIN_EXPIRE_MS = 2 * 60 * 60 * 1000;
    private static final long OTP_EXPIRE_MS   = 60 * 1000;
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of(role));
        claims.put("type", "AUTH");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + LOGIN_EXPIRE_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && "AUTH".equals(extractAllClaims(token).get("type"));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateOtpToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("type", "OTP")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + OTP_EXPIRE_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims verifyOtp(String otpToken) {
        Claims claims = extractAllClaims(otpToken);

        if (!"OTP".equals(claims.get("type"))) {
            throw new RuntimeException("Invalid OTP type");
        }

        return claims;
    }


    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }
    public Claims extractAllClaimsIgnoreExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
