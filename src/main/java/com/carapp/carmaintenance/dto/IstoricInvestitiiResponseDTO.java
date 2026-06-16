package com.carapp.carmaintenance.dto;

import java.time.LocalDate;
import java.util.Set;

public class IstoricInvestitiiResponseDTO {
    private Long id;
    private LocalDate dataInvestitie;
    private String titlu;
    private String descriere;
    private Double costTotal;
    private Double manopera;
    private Integer kilometrajLaInvestitie;



    private Set<PiesaResponseDTO> piese;

    public IstoricInvestitiiResponseDTO(Long id, LocalDate dataInvestitie, String titlu, String descriere,
                                        Double costTotal, Double manopera, Integer kilometrajLaInvestitie,
                                        Set<PiesaResponseDTO> piese) {
        this.id = id;
        this.dataInvestitie = dataInvestitie;
        this.titlu = titlu;
        this.descriere = descriere;
        this.costTotal = costTotal;
        this.manopera = manopera;
        this.kilometrajLaInvestitie = kilometrajLaInvestitie;
        this.piese = piese;
    }

    public Long getId() { return id; }
    public LocalDate getDataInvestitie() { return dataInvestitie; }
    public String getTitlu() { return titlu; }
    public String getDescriere() { return descriere; }
    public Double getCostTotal() { return costTotal; }
    public Double getManopera() { return manopera; }
    public Set<PiesaResponseDTO> getPiese() { return piese; }
    public Integer getKilometrajLaInvestitie() { return kilometrajLaInvestitie; }

}