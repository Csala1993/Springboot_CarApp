package com.carapp.carmaintenance.dto;

public class MasinaListDTO {
    private Long id;
    private Integer an;
    private String marca;
    private String vin;

    private Long proprietarId;
    private String proprietarNume;

    public MasinaListDTO(Long id, Integer an, String marca, String vin, Long proprietarId, String proprietarNume) {
        this.id = id;
        this.an = an;
        this.marca = marca;
        this.vin = vin;
        this.proprietarId = proprietarId;
        this.proprietarNume = proprietarNume;
    }

    public Long getId() { return id; }
    public Integer getAn() { return an; }
    public String getMarca() { return marca; }
    public String getVin() { return vin; }
    public Long getProprietarId() { return proprietarId; }
    public String getProprietarNume() { return proprietarNume; }
}