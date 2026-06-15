package com.carapp.carmaintenance.dto;

public class AdminUserDTO {

    private Long id;
    private String nume;
    private String email;
    private String role;

    public AdminUserDTO(Long id, String nume, String email, String role) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}