package com.mruruc.controller;

import com.mruruc.model.User;
import com.mruruc.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

//@RestController
//@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

  //  @GetMapping("/resource")
    public ResponseEntity<?> auth() {
        return ResponseEntity.ok(Map.of("Resource:", "...****..Secret :)"));
    }


  //  @PostMapping("/auth")
    public ResponseEntity<URI> createUser(@RequestBody User user) {
        String token = userService.saveUser(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, token);
        return new ResponseEntity<>(URI.create("/api/resource"), httpHeaders, HttpStatus.CREATED);
    }
}
