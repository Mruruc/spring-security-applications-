package com.mruruc.auth.api_key_based_auth;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ApiKeyAuthProvider implements AuthenticationProvider {
    @Value("${secret.key}")
    private String apiKey;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String credentials = (String) authentication.getCredentials();
        if (credentials.equals(apiKey)) {
            authentication.setAuthenticated(true);
            return authentication;
        }
        throw new BadCredentialsException("Not Valid Api Key!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
