package com.mruruc.auth.jwt_based_auth;

import com.mruruc.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    public JwtFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        final String servletPath = request.getServletPath();
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (servletPath.equals("/api/resource") && isTokenExist(authHeader)) {
            SecurityContext context = SecurityContextHolder.getContext();

            if (context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {

                String token = this.getToken(authHeader);
                // validate token and getPayload
                Claims payload = jwtService.getPayload(token);
                UserDetails userDetails = userService.loadUserByUsername(payload.getSubject());

                var authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                context.setAuthentication(authentication);

            }
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(String authHeader) {
        return authHeader.substring(7);
    }

    private boolean isTokenExist(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }
}
