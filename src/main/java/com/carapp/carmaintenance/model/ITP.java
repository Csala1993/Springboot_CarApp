package com.carapp.carmaintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Transient;
import java.time.temporal.ChronoUnit;

import java.time.LocalDate;

@Entity
@Table(name = "itp")
public class ITP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataEfectuare;

    @Column(nullable = false)
    private LocalDate dataExpirare;

    @OneToOne(mappedBy = "itp")
    @JsonIgnore
    private Masina masina;


    public ITP() {}

    public ITP(LocalDate dataEfectuare) {
        this.dataEfectuare = dataEfectuare;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataEfectuare() { return dataEfectuare; }
    public void setDataEfectuare(LocalDate dataEfectuare) { this.dataEfectuare = dataEfectuare; }

    public LocalDate getDataExpirare() { return dataExpirare; }
    public void setDataExpirare(LocalDate dataExpirare) { this.dataExpirare = dataExpirare; }

    public Masina getMasina() { return masina; }
    public void setMasina(Masina masina) { this.masina = masina; }

    @Transient
    public long getZileRamase() {
        return ChronoUnit.DAYS.between(LocalDate.now(), dataExpirare);
    }
}




