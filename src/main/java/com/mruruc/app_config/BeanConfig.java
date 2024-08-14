package com.mruruc.app_config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;

@Configuration
public class BeanConfig {
    @Value("${application.security.SECRET_KEY}")
    private String STRING_KEY;
    @Value("${application.security.ALGORITHM}")
    private String ALGORITHM;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Key SECRET_KEY() {
        //new SecretKeySpec(Base64.getDecoder().decode(STRING_KEY),ALGORITHM);
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(STRING_KEY));
    }
}
