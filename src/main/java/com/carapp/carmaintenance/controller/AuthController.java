package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.dto.LoginRequest;
import com.carapp.carmaintenance.dto.LoginResponse;
import com.carapp.carmaintenance.dto.RegisterRequestDTO;
import com.carapp.carmaintenance.model.User;
import com.carapp.carmaintenance.service.AuthService;
import com.carapp.carmaintenance.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        try {
            User user = new User();
            user.setNume(request.getNume());
            user.setEmail(request.getEmail());
            user.setParola(request.getParola());

            userService.createUser(user); // hash-ul si verificarea emailului se fac aici
            return ResponseEntity.ok("Cont creat cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}