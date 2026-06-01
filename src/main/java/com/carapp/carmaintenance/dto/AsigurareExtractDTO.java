package com.carapp.carmaintenance.dto;

import java.time.LocalDate;

public class AsigurareExtractDTO {
    private String numeAsigurator;
    private String vinMasina;
    private String numeProprietar;
    private String numarInmatriculare;
    private LocalDate dataInceput;
    private LocalDate dataIncheiere;

    public String getNumeAsigurator() { return numeAsigurator; }
    public void setNumeAsigurator(String numeAsigurator) { this.numeAsigurator = numeAsigurator; }

    public String getVinMasina() { return vinMasina; }
    public void setVinMasina(String vinMasina) { this.vinMasina = vinMasina; }

    public String getNumeProprietar() { return numeProprietar; }
    public void setNumeProprietar(String numeProprietar) { this.numeProprietar = numeProprietar; }

    public String getNumarInmatriculare() { return numarInmatriculare; }
    public void setNumarInmatriculare(String numarInmatriculare) { this.numarInmatriculare = numarInmatriculare; }

    public LocalDate getDataInceput() { return dataInceput; }
    public void setDataInceput(LocalDate dataInceput) { this.dataInceput = dataInceput; }

    public LocalDate getDataIncheiere() { return dataIncheiere; }
    public void setDataIncheiere(LocalDate dataIncheiere) { this.dataIncheiere = dataIncheiere; }
}