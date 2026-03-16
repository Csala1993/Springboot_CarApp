package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.dto.LoginRequest;
import com.carapp.carmaintenance.dto.LoginResponse;
import com.carapp.carmaintenance.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String test() {
        return "AUTH CONTROLLER OK - Public endpoint";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            e.printStackTrace(); // TEMP
            return ResponseEntity.badRequest().body(e.getClass().getName() + ": " + e.getMessage());
        }

    }



}