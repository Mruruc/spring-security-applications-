package com.mruruc.auth.custome_auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Service
public class CustomAuthProvider implements AuthenticationManager {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

  //  @Autowired
    public CustomAuthProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        passwordMatchCheck(userDetails.getPassword(), authentication.getCredentials());
        authentication.setAuthenticated(true);
        return authentication;
    }

    private void passwordMatchCheck(String password, Object rawPassword) throws AuthenticationException {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        boolean matches = passwordEncoder.matches((CharSequence) rawPassword, password);
        if (!matches) {
            throw new BadCredentialsException("Password mismatched !");
        }
    }
}
