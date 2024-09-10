package com.raphael.philosophy.service;

import com.raphael.philosophy.configuration.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTService {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // Generate a secure key for HS256
    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private long jwtExpirationInMs = 86400000;
    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        Instant now = Instant.now();
        Instant expiryInstant = now.plus(jwtExpirationInMs, ChronoUnit.MILLIS);
        Date expiryDate = Date.from(expiryInstant);

        String token = Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            logger.warn("JWT expired");
        } catch (Exception ex) {
            logger.error("Invalid JWT", ex);
        }
        return false;
    }

    public UserDetails loadUserByUsername(String username) {
        return userDetailsService.loadUserByUsername(username);
    }
}