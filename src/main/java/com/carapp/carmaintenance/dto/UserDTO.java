package com.carapp.carmaintenance.dto;

import java.util.List;

public class UserDTO {

    private Long id;
    private String nume;
    private String email;
    private String parola;
    private List<MasinaDTO> masini;

    public UserDTO(Long id, String nume, String email, String parola, List<MasinaDTO> masini) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
        this.masini = masini;
    }

    public Long getId() { return id; }
    public String getNume() { return nume; }
    public String getEmail() { return email; }
    public String getParola() { return parola; }
    public List<MasinaDTO> getMasini() { return masini; }
}