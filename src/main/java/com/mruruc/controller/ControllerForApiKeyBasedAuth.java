package com.mruruc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ControllerForApiKeyBasedAuth {

    @GetMapping("/api/v2")
    public ResponseEntity<Map<String, String>> resource() {
        return new ResponseEntity<>(Map.of("resource", "secret :)"), HttpStatus.OK);
    }
}
