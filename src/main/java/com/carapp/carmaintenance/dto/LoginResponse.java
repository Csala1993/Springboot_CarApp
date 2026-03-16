package com.carapp.carmaintenance.dto;

public class LoginResponse {
    public String token;
    public String email;
    public String role;
    public Long userId;

    public LoginResponse(String token, String email, String role,
                          Long userId) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.userId = userId;
    }
}