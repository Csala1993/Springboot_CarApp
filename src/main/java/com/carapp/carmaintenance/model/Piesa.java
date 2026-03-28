package com.carapp.carmaintenance.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "piese")
public class Piesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nume;

    @Column(nullable = false)
    private Double pret;

    @Column(nullable = false)
    private String distribuitor;

    @ManyToMany(mappedBy = "pieseSchimbate")
    @JsonIgnore
    private List<IstoricService> servicii = new ArrayList<>();

    // Constructori
    public Piesa() {}

    public Piesa(String nume, Double pret, String distribuitor) {
        this.nume = nume;
        this.pret = pret;
        this.distribuitor = distribuitor;
    }

    // Getters și Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public String getDistribuitor() {
        return distribuitor;
    }

    public void setDistribuitor(String distribuitor) {
        this.distribuitor = distribuitor;
    }

    public List<IstoricService> getServicii() {
        return servicii;
    }

    public void setServicii(List<IstoricService> servicii) {
        this.servicii = servicii;
    }


}