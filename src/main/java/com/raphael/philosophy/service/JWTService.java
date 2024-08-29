package com.raphael.philosophy.service;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Service
public class JWTService {


    private String secretKey = "secdf.vfd514fd5v1fdv###€@#{[ret";

    private long jwtExpirationInMs = 86400000;

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        // Log username
        System.out.println("Generating token for user: " + userPrincipal.getUsername());

        Instant now = Instant.now();
        Instant expiryInstant = now.plus(jwtExpirationInMs, ChronoUnit.MILLIS);
        Date expiryDate = Date.from(expiryInstant);

        // Log expiration time
        System.out.println("Token expiration date: " + expiryDate);

        SecretKey key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

        String token = Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Log generated token
        System.out.println("Generated JWT Token: " + token);

        return token;
    }


}

