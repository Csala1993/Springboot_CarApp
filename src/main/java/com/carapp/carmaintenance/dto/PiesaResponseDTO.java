package com.carapp.carmaintenance.dto;

public class PiesaResponseDTO {
    private Long id;
    private String nume;
    private Double pret;

    public PiesaResponseDTO() {}
    public PiesaResponseDTO(Long id, String nume, Double pret) {
        this.id = id; this.nume = nume; this.pret = pret;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public Double getPret() { return pret; }
    public void setPret(Double pret) { this.pret = pret; }
}
