package com.shortbreakshub.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final Key key;
    private final long expiryMs;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiryMs}") long expiryMs
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiryMs = expiryMs;
    }

    /** Generate a token with user info inside */
    public String generateToken(Long userId, String email, String displayName, String role) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiryMs);

        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // "sub" claim
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(Map.of(
                        "email", email,
                        "displayName", displayName,
                        "role", role
                ))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** Parse & validate token */
    public Jws<Claims> parse(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    /** Convenience methods */
    public Long getUserId(String token) {
        return Long.valueOf(parse(token).getBody().getSubject());
    }

    public String getEmail(String token) {
        return parse(token).getBody().get("email", String.class);
    }

    public String getRole(String token) {
        return parse(token).getBody().get("role", String.class);
    }

    public String getDisplayName(String token) {
        return parse(token).getBody().get("displayName", String.class);
    }
}
