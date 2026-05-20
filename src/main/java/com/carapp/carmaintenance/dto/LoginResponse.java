package com.carapp.carmaintenance.dto;

public class LoginResponse {
    public String token;
    public String email;
    public String role;
    public Long userId;
    public String nume;

    public LoginResponse(String token, String email, String role, Long userId, String nume) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.userId = userId;
        this.nume = nume;
    }
}
