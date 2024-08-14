package com.mruruc.auth.jwt_based_auth;

public class JwtTokenNotValidException extends RuntimeException {
    public JwtTokenNotValidException(String message) {
        super(message);
    }
}
