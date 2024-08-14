package com.mruruc.auth.inMemory_based_auth;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
public class InMemoryAuthConfig {

    // Authentication
    //  @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("Mr.Uruc")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        UserDetails user2 = User.builder()
                .username("John")
                .password(passwordEncoder().encode("password21"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, user2);
    }

    // @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authorization
    // @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/public/**").permitAll()
                        .requestMatchers("/user-level").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/**").hasRole("ADMIN")
                        //.anyRequest().hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .logout(LogoutConfigurer::permitAll);

        return httpSecurity.build();
    }
}
