package com.mruruc.auth.api_key_based_auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class AuthConfig {

    private final AuthFilter authFilter;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationProvider authenticationProvider;


    @Autowired
    public AuthConfig(AuthFilter authFilter,
                      AuthenticationManager authenticationManager,
                      AuthenticationProvider authenticationProvider) {
        this.authFilter = authFilter;
        this.authenticationManager = authenticationManager;
        this.authenticationProvider = authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager)
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(authManger ->
                        authManger.anyRequest().authenticated()
                );
        return httpSecurity.build();
    }


}
