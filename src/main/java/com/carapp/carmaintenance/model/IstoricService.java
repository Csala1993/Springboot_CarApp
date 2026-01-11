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

    // Constructori
    public IstoricService() {}

    public IstoricService(LocalDate dataService, Integer kilometrajLaService, String descriere) {
        this.dataService = dataService;
        this.kilometrajLaService = kilometrajLaService;
        this.descriere = descriere;
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

    // Metode helper
    public void adaugaPiesa(Piesa piesa) {
        pieseSchimbate.add(piesa);
    }

    public void stergePiesa(Piesa piesa) {
        pieseSchimbate.remove(piesa);
    }

    // Calculează costul total din piese
    public void calculeazaCostTotal() {
        this.costTotal = pieseSchimbate.stream()
                .mapToDouble(Piesa::getPret)
                .sum();
    }
}