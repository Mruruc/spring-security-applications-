package com.mruruc.auth.api_key_based_auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

@Configuration
public class AuthBeanConfig {

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new ApiKeyAuthProvider();
    }

    @Bean
    public ProviderManager providerManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return providerManager();
    }
}
