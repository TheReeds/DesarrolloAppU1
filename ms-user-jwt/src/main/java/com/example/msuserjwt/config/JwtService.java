package com.example.msuserjwt.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "4c05bb017ba37d8aac9e6380f1d8e6d102495d2a33b5d21f1aa5a01da99d21a3";

    public String generateToken(UserDetails userDetails, Integer userId) {
        Map<String, Object> extraclaims = new HashMap<>();
        extraclaims.put("userId", userId);
        return generateToken(extraclaims, userDetails);
    }

    public String generateToken(Map<String, Object> extraclaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraclaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 día
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String getUserName(String token){
        return getClaim(token, Claims::getSubject);
    }
    public Integer getUserId(String token) {
        return getClaim(token, claims -> (Integer) claims.get("userId"));
    }
    public <T>T getClaim(String token, Function<Claims,T> ClaimsResolver){
        final Claims claims = getAllClaims(token);
        return ClaimsResolver.apply(claims);
    }
    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean validateToken(String token, UserDetails userDetails){
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){ return getExpiration(token).before(new Date());}

    private Date getExpiration(String token){ return getClaim(token, Claims::getExpiration);}

}

