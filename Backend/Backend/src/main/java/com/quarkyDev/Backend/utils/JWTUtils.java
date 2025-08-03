package com.quarkyDev.Backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTUtils {

    private final SecretKey SECRET_KEY;
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;       // 15 minutes
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days

    private final RestClient.Builder builder;

    public JWTUtils(RestClient.Builder builder) {
        String secretString = "4fd63a46f51e1f8b3a2cc889d3e1730caa95b19f3ec7e2e0b1f5f94aa0a7dc8366c9e2de9cd29259a77e5c850c95e5806a93975d61cbefee76f3f96373310154";
        byte[] keyBytes = hexStringToByteArray(secretString);
        this.SECRET_KEY = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.builder = builder;
    }

    // Generate access token (short-lived)
    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .claim("type", "access")
                .signWith(SECRET_KEY)
                .compact();
    }

    // Generate refresh token (long-lived)
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .claim("type", "refresh")
                .signWith(SECRET_KEY)
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Extract expiration
    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    // Extract roles
    public List<String> extractRoles(String token) {
        return extractClaims(token, claims -> claims.get("roles", List.class));
    }

    // Extract token type: "access" or "refresh"
    public String extractTokenType(String token) {
        return extractClaims(token, claims -> claims.get("type", String.class));
    }

    // Generic claim extraction
    private <T> T extractClaims(String token, Function<Claims, T> claimsFunction) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claimsFunction.apply(claims);
        } catch (Exception e) {
            throw new SignatureException("Invalid JWT token: " + e.getMessage());
        }
    }

    // Validate token's integrity and expiration
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Utility to decode hex string (Java 11+ compatible)
    private byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
