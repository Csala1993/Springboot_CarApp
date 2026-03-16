package com.carapp.carmaintenance.dto;

import java.time.LocalDate;
import java.util.List;

public class IstoricServiceRequestDTO {
    private LocalDate dataService;
    private Integer kilometrajLaService;
    private String descriere;
    private String serviceAuto;
    private Double manopera;
    private List<Long> pieseIds;

    public LocalDate getDataService() { return dataService; }
    public void setDataService(LocalDate dataService) { this.dataService = dataService; }
    public Integer getKilometrajLaService() { return kilometrajLaService; }
    public void setKilometrajLaService(Integer kilometrajLaService) { this.kilometrajLaService = kilometrajLaService; }
    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }
    public String getServiceAuto() { return serviceAuto; }
    public void setServiceAuto(String serviceAuto) { this.serviceAuto = serviceAuto; }
    public List<Long> getPieseIds() { return pieseIds; }
    public void setPieseIds(List<Long> pieseIds) { this.pieseIds = pieseIds; }
    public Double getManopera() { return manopera; }
    public void setManopera(Double manopera) { this.manopera = manopera; }
}