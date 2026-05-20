package com.carapp.carmaintenance.dto;

public class PiesaSearchResultDTO {
    private String titlu;
    private String pret;
    private String magazin;
    private String link;
    private String imagine;

    public PiesaSearchResultDTO() {}

    public PiesaSearchResultDTO(String titlu, String pret, String magazin, String link, String imagine) {
        this.titlu = titlu;
        this.pret = pret;
        this.magazin = magazin;
        this.link = link;
        this.imagine = imagine;
    }

    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getPret() { return pret; }
    public void setPret(String pret) { this.pret = pret; }

    public String getMagazin() { return magazin; }
    public void setMagazin(String magazin) { this.magazin = magazin; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getImagine() { return imagine; }
    public void setImagine(String imagine) { this.imagine = imagine; }
}