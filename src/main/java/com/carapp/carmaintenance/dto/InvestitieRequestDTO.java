package com.carapp.carmaintenance.dto;

import java.time.LocalDate;
import java.util.List;

public class InvestitieRequestDTO {
    private LocalDate dataInvestitie;
    private String titlu;
    private String descriere;
    private Double manopera;
    private List<PiesaRequestDTO> piese; // <- înlocuiește List<Long> piesaIds

    public LocalDate getDataInvestitie() { return dataInvestitie; }
    public void setDataInvestitie(LocalDate dataInvestitie) { this.dataInvestitie = dataInvestitie; }

    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }

    public Double getManopera() { return manopera; }
    public void setManopera(Double manopera) { this.manopera = manopera; }

    public List<PiesaRequestDTO> getPiese() { return piese; }
    public void setPiese(List<PiesaRequestDTO> piese) { this.piese = piese; }
}