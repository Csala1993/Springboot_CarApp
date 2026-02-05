package com.carapp.carmaintenance.dto;

import java.time.LocalDate;
import java.util.List;

public class IstoricInvestitiiSimpleDTO {
    private Long id;
    private LocalDate dataInvestitie;
    private String titlu;
    private String descriere;
    private Double manopera;
    private Double costTotal;

    // trimiți doar id + nume + pret (minimal)
    private List<PiesaMiniDTO> piese;

    public IstoricInvestitiiSimpleDTO() {}

    public IstoricInvestitiiSimpleDTO(Long id, LocalDate dataInvestitie, String titlu, String descriere,
                                      Double manopera, Double costTotal, List<PiesaMiniDTO> piese) {
        this.id = id;
        this.dataInvestitie = dataInvestitie;
        this.titlu = titlu;
        this.descriere = descriere;
        this.manopera = manopera;
        this.costTotal = costTotal;
        this.piese = piese;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataInvestitie() { return dataInvestitie; }
    public void setDataInvestitie(LocalDate dataInvestitie) { this.dataInvestitie = dataInvestitie; }

    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }

    public Double getManopera() { return manopera; }
    public void setManopera(Double manopera) { this.manopera = manopera; }

    public Double getCostTotal() { return costTotal; }
    public void setCostTotal(Double costTotal) { this.costTotal = costTotal; }

    public List<PiesaMiniDTO> getPiese() { return piese; }
    public void setPiese(List<PiesaMiniDTO> piese) { this.piese = piese; }
}
