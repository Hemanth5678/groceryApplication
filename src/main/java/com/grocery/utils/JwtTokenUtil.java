package com.grocery.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.grocery.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    @Value("${grocery.app.jwtSecret}")
    private String secretKey;
    @Value("${grocery.app.jwtExpirationInMs}")
    private long accessTokenExpiredDate;
    @Value("${grocery.app.refreshTokenExpirationInMs}")
    private long refreshTokenExpiredDate;

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("name", "ACCESS_TOKEN")
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiredDate))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("name", "REFRESH_TOKEN")
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiredDate * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public boolean validateTokenHeader(JwsHeader header) {
        return header.getType().equals("JWT") &&
                header.get("name").equals("ACCESS_TOKEN") &&
                header.getAlgorithm().equals("HS256");
    }

    public Jws<Claims> getJwsClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    public long getUserIdFromClaims(Claims claims) {
        return Long.parseLong(claims.get("userId").toString());
    }

}
