package com.carapp.carmaintenance.dto;

public class PiesaRequestDTO {
    private String nume;
    private Double pret;
    private String distribuitor;

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public Double getPret() { return pret; }
    public void setPret(Double pret) { this.pret = pret; }

    public String getDistribuitor() { return distribuitor; }
    public void setDistribuitor(String distribuitor) { this.distribuitor = distribuitor; }
}