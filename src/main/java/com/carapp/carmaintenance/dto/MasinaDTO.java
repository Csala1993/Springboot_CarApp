package com.carapp.carmaintenance.dto;

public class MasinaDTO {
    private Long id;
    private String marca;
    private String model;
    private Integer an;
    private String numarInmatriculare;
    private String vin;
    private Integer kilometraj;

    // Constructori
    public MasinaDTO() {}

    public MasinaDTO(Long id, String marca, String model, Integer an,
                     String numarInmatriculare, String vin, Integer kilometraj) {
        this.id = id;
        this.marca = marca;
        this.model = model;
        this.an = an;
        this.numarInmatriculare = numarInmatriculare;
        this.vin = vin;
        this.kilometraj = kilometraj;
    }

    // Getters și Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }

    public String getNumarInmatriculare() {
        return numarInmatriculare;
    }

    public void setNumarInmatriculare(String numarInmatriculare) {
        this.numarInmatriculare = numarInmatriculare;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getKilometraj() {
        return kilometraj;
    }

    public void setKilometraj(Integer kilometraj) {
        this.kilometraj = kilometraj;
    }
}