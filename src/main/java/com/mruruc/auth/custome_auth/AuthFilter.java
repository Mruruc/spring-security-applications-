package com.mruruc.auth.custome_auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Service
public class AuthFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if ("post".equalsIgnoreCase(request.getMethod())) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username != null && password != null) {

                var token = new UsernamePasswordAuthenticationToken(username, password);
                Authentication authenticate = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authenticate);

            }
        }

        filterChain.doFilter(request, response);
    }
}
