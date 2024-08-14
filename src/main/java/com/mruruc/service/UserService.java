package com.mruruc.service;

import com.mruruc.auth.jwt_based_auth.JwtService;
import com.mruruc.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

//@Service
public class UserService implements UserDetailsService {
    private final List<User> users = new LinkedList<>();
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }

    public String saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.users.add(user);
        return jwtService.generateToken(user);
    }
}
