package com.mruruc.auth.jwt_based_auth;

import com.mruruc.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

//@Service
public class JwtService {
    @Value("${application.security.JWT_TOKEN_EXPIRATION_DATE}")
    private long tokenExpirationDate;
    private final Key SECRET_KEY;

    public JwtService(Key SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public Claims getPayload(String token) {
        Claims payload = Jwts.parser()
                .verifyWith((SecretKey) SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        isTokenExpired(payload.getExpiration());
        return payload;
    }

    private void isTokenExpired(Date expiration) {
        if (new Date().after(expiration)) throw new JwtTokenNotValidException("Token is expired.");
    }


    /**
     * Generate token with just subject.
     * @param subject
     * @return JWS TOKEN
     */
    public String generateToken(String subject) {
        String token = Jwts.builder()
                .issuedAt(new Date())
                .subject(subject)
                .expiration(getExpirationDate())
                .signWith(SECRET_KEY)
                .compact();
        return "Bearer ".concat(token);
    }

    /**
     * Generate token with user object, add authorities of user object to token.
     * @param user
     * @return JWS TOKEN.
     */
    public String generateToken(User user) {
        List<String> authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        String token = Jwts.builder()
                .issuedAt(new Date())
                .subject(user.getUsername())
                .claim("authorities", authorities)
                .expiration(getExpirationDate())
                .signWith(SECRET_KEY)
                .compact();
        return "Bearer ".concat(token);
    }

    /**
     * Generate token with user object, add authorities of user object to token and add extra claims.
     * @param user
     * @param extraClaims
     * @return JWS TOKEN
     */
    public String generateToken(User user, Map<String, Object> extraClaims) {
        List<String> authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        String token = Jwts.builder()
                .issuedAt(new Date())
                .subject(user.getUsername())
                .claims(extraClaims)
                .claim("authorities", authorities)
                .expiration(getExpirationDate())
                .signWith(SECRET_KEY)
                .compact();
        return "Bearer ".concat(token);
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + tokenExpirationDate);
    }


}
