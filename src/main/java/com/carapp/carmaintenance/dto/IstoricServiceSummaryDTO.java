package com.carapp.carmaintenance.dto;

import java.time.LocalDate;
import java.util.List;

public class IstoricServiceSummaryDTO {
    private Long id;
    private LocalDate dataService;
    private Integer kilometrajLaService;
    private String descriere;
    private String serviceAuto;
    private Double manopera;
    private Double costTotal;
    private List<PiesaResponseDTO> pieseSchimbate; // ← era List<Piesa>

    public IstoricServiceSummaryDTO() {}

    public IstoricServiceSummaryDTO(Long id, LocalDate dataService, Integer kilometrajLaService,
                                    String descriere, String serviceAuto, Double costTotal,
                                    Double manopera, List<PiesaResponseDTO> pieseSchimbate) {
        this.id = id;
        this.dataService = dataService;
        this.kilometrajLaService = kilometrajLaService;
        this.descriere = descriere;
        this.serviceAuto = serviceAuto;
        this.costTotal = costTotal;
        this.manopera = manopera; // ADAUGĂ
        this.pieseSchimbate = pieseSchimbate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getManopera() { return manopera; }
    public void setManopera(Double manopera) { this.manopera = manopera; }
    public LocalDate getDataService() { return dataService; }
    public void setDataService(LocalDate dataService) { this.dataService = dataService; }
    public Integer getKilometrajLaService() { return kilometrajLaService; }
    public void setKilometrajLaService(Integer kilometrajLaService) { this.kilometrajLaService = kilometrajLaService; }
    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }
    public String getServiceAuto() { return serviceAuto; }
    public void setServiceAuto(String serviceAuto) { this.serviceAuto = serviceAuto; }
    public Double getCostTotal() { return costTotal; }
    public void setCostTotal(Double costTotal) { this.costTotal = costTotal; }
    public List<PiesaResponseDTO> getPieseSchimbate() { return pieseSchimbate; }
    public void setPieseSchimbate(List<PiesaResponseDTO> pieseSchimbate) { this.pieseSchimbate = pieseSchimbate; }
}