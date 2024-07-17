package com.dg.cafe.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private String secreteKey = "3ed62d4926450d6fb1591da05d4e049034372a4e744a2d8a0f330232feff6004";

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token)
    {
        return Jwts.parser().setSigningKey(secreteKey).parseClaimsJws(token).getBody();
    }
    public String generateToken(String username, String role)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",role);
        return createToken(claims,username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256, secreteKey).compact();
    }


    public String extractUsername(String token)
    {
        return extractClaims(token, Claims::getSubject);
    }
    public Date extractExpiration(String token)
    {
        return extractClaims(token, Claims::getExpiration);
    }
    private Boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
