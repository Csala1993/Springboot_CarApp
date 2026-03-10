package com.carapp.carmaintenance.dto;

import java.time.LocalDate;
import java.util.Set;

public class IstoricInvestitiiSimpleDTO {
    private Long id;
    private LocalDate dataInvestitie;
    private String titlu;
    private String descriere;
    private Double costTotal;
    private Double manopera;


    private Set<PiesaMiniDTO> piese;

    public IstoricInvestitiiSimpleDTO(Long id, LocalDate dataInvestitie, String titlu, String descriere,
                                      Double costTotal, Double manopera, Set<PiesaMiniDTO> piese) {
        this.id = id;
        this.dataInvestitie = dataInvestitie;
        this.titlu = titlu;
        this.descriere = descriere;
        this.costTotal = costTotal;
        this.manopera = manopera;
        this.piese = piese;
    }

    public Long getId() { return id; }
    public LocalDate getDataInvestitie() { return dataInvestitie; }
    public String getTitlu() { return titlu; }
    public String getDescriere() { return descriere; }
    public Double getCostTotal() { return costTotal; }
    public Double getManopera() { return manopera; }
    public Set<PiesaMiniDTO> getPiese() { return piese; }
}