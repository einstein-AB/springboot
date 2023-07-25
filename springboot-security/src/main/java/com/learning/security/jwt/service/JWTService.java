package com.learning.security.jwt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private long JWT_TOKEN_VALIDITY = 1000*60*15;

    public String generateJwtToken (String username) {

        String JWT_token = Jwts.builder()
                .addClaims(new HashMap<>())  //claims = body (other information about the request)
                .setSubject(username)       //subject = username

                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) //15m token expiration time

                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();

        return JWT_token;
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
