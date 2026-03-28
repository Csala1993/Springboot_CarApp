package com.carapp.carmaintenance.dto;

import java.time.LocalDate;
import java.util.List;

public class IstoricServiceRequestDTO {
    private LocalDate dataService;
    private Integer kilometrajLaService;
    private String descriere;
    private String serviceAuto;
    private Double manopera;
    private List<PiesaRequestDTO> piese; // <- câmpul corect

    public LocalDate getDataService() { return dataService; }
    public void setDataService(LocalDate dataService) { this.dataService = dataService; }

    public Integer getKilometrajLaService() { return kilometrajLaService; }
    public void setKilometrajLaService(Integer kilometrajLaService) { this.kilometrajLaService = kilometrajLaService; }

    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }

    public String getServiceAuto() { return serviceAuto; }
    public void setServiceAuto(String serviceAuto) { this.serviceAuto = serviceAuto; }

    public Double getManopera() { return manopera; }
    public void setManopera(Double manopera) { this.manopera = manopera; }

    public List<PiesaRequestDTO> getPiese() { return piese; } // <- getter corect
    public void setPiese(List<PiesaRequestDTO> piese) { this.piese = piese; } // <- setter corect
}