package com.carapp.carmaintenance.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "istoric_service")
public class IstoricService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataService;

    @Column(nullable = false)
    private Integer kilometrajLaService;

    @Column(length = 1000)
    private String descriere;

    @Column
    private String serviceAuto; // Numele service-ului auto unde s-a făcut

    @Column
    private Double manopera;

    @Column
    private Double costTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masina_id", nullable = false)
    @JsonIgnore
    private Masina masina;

    @ManyToMany
    @JoinTable(
            name = "service_piese",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "piesa_id")
    )
    private List<Piesa> pieseSchimbate = new ArrayList<>();


    public IstoricService() {}

    public IstoricService(LocalDate dataService, Integer kilometrajLaService, String descriere) {
        this.dataService = dataService;
        this.kilometrajLaService = kilometrajLaService;
        this.descriere = descriere;
    }


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

    public Double getManopera() { return manopera; }

    public void setManopera(Double manopera) { this.manopera = manopera; }

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

    public Masina getMasina() {
        return masina;
    }

    public void setMasina(Masina masina) {
        this.masina = masina;
    }

    public List<Piesa> getPieseSchimbate() {
        return pieseSchimbate;
    }

    public void setPieseSchimbate(List<Piesa> pieseSchimbate) {
        this.pieseSchimbate = pieseSchimbate;
    }


    public void adaugaPiesa(Piesa piesa) {
        pieseSchimbate.add(piesa);
    }

    public void stergePiesa(Piesa piesa) {
        pieseSchimbate.remove(piesa);
    }


    public void calculeazaCostTotal() {
        double costPiese = pieseSchimbate.stream()
                .mapToDouble(Piesa::getPret)
                .sum();
        this.costTotal = costPiese + (manopera != null ? manopera : 0.0);
    }
}