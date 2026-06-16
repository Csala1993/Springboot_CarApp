package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.dto.ErrorResponseDTO;
import com.carapp.carmaintenance.dto.LoginRequestDTO;
import com.carapp.carmaintenance.dto.LoginResponseDTO;
import com.carapp.carmaintenance.dto.RegisterRequestDTO;
import com.carapp.carmaintenance.model.User;
import com.carapp.carmaintenance.service.AuthService;
import com.carapp.carmaintenance.service.UserService;
import com.carapp.carmaintenance.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final EmailService emailService;

    public AuthController(AuthService authService, UserService userService, EmailService emailService) {
        this.authService = authService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/test")
    public String test() {
        return "AUTH CONTROLLER OK - Public endpoint";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            LoginResponseDTO response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(401)
                    .body(new ErrorResponseDTO("Email sau parola incorecta"));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        try {
            User user = new User();
            user.setNume(request.getNume());
            user.setEmail(request.getEmail());
            user.setParola(request.getParola());

            userService.createUser(user);
            emailService.trimiteEmailBunVenit(user.getEmail(), user.getNume());

            return ResponseEntity.ok("Cont creat cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}