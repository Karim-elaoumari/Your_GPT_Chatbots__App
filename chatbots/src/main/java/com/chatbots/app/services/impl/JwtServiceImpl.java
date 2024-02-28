package com.chatbots.app.services.impl;

import com.chatbots.app.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${application.token.signing.key}")
    private  String jwtSigningKey;
    @Value("${application.token.signing.access-token-expiration}")
    private String accessTokenExpirationTime;
    @Value("${application.token.signing.refresh-token-expiration}")
    private String refreshTokenExpirationTime;
    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = Map.of("type", "access");
        return generateToken(claims, userDetails,getAccessTokenExpirationTime());
    }
    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = Map.of("type", "refresh");
        return generateToken(claims, userDetails,getRefreshTokenExpirationTime());
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails,Integer expiration) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }
    private Claims extractAllClaims(String token) {
            return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        final String type = extractType(token);
        if(type == null || userName == null) return false;
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token) && type.equals("access");
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private String extractType(String token) {
        return extractClaim(token, claims -> claims.get("type", String.class));
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Integer getAccessTokenExpirationTime() {
        return Integer.parseInt(accessTokenExpirationTime);
    }
    private Integer getRefreshTokenExpirationTime() {
        return Integer.parseInt(refreshTokenExpirationTime);
    }
}
