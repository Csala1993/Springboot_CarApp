package com.carapp.carmaintenance.dto;

import com.carapp.carmaintenance.model.Piesa;
import java.time.LocalDate;
import java.util.List;

public class IstoricServiceSimpleDTO {
    private Long id;
    private LocalDate dataService;
    private Integer kilometrajLaService;
    private String descriere;
    private String serviceAuto;
    private Double costTotal;
    private List<Piesa> pieseSchimbate;

    // Constructori
    public IstoricServiceSimpleDTO() {}

    public IstoricServiceSimpleDTO(Long id, LocalDate dataService, Integer kilometrajLaService,
                                   String descriere, String serviceAuto, Double costTotal,
                                   List<Piesa> pieseSchimbate) {
        this.id = id;
        this.dataService = dataService;
        this.kilometrajLaService = kilometrajLaService;
        this.descriere = descriere;
        this.serviceAuto = serviceAuto;
        this.costTotal = costTotal;
        this.pieseSchimbate = pieseSchimbate;
    }

    // Getters și Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataService() {
        return dataService;
    }

    public void setDataService(LocalDate dataService) {
        this.dataService = dataService;
    }

    public Integer getKilometrajLaService() {
        return kilometrajLaService;
    }

    public void setKilometrajLaService(Integer kilometrajLaService) {
        this.kilometrajLaService = kilometrajLaService;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getServiceAuto() {
        return serviceAuto;
    }

    public void setServiceAuto(String serviceAuto) {
        this.serviceAuto = serviceAuto;
    }

    public Double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Double costTotal) {
        this.costTotal = costTotal;
    }

    public List<Piesa> getPieseSchimbate() {
        return pieseSchimbate;
    }

    public void setPieseSchimbate(List<Piesa> pieseSchimbate) {
        this.pieseSchimbate = pieseSchimbate;
    }
}