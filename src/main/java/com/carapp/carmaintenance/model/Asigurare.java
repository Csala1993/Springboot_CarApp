package com.carapp.carmaintenance.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

@Entity
@Table(name = "asigurari")
public class Asigurare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataInceput;

    @Column(nullable = false)
    private LocalDate dataIncheiere;

    @Column(nullable = false)
    private String numeAsigurator;

    @Column(nullable = false)
    private String vinMasina;

    @Column(nullable = false)
    private String numeProprietar;

    @OneToOne(mappedBy = "asigurare")
    @JsonIgnore
    private Masina masina;


    public Asigurare() {}

    public Asigurare(LocalDate dataInceput, LocalDate dataIncheiere,
                     String numeAsigurator, String vinMasina, String numeProprietar) {
        this.dataInceput = dataInceput;
        this.dataIncheiere = dataIncheiere;
        this.numeAsigurator = numeAsigurator;
        this.vinMasina = vinMasina;
        this.numeProprietar = numeProprietar;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInceput() {
        return dataInceput;
    }

    public void setDataInceput(LocalDate dataInceput) {
        this.dataInceput = dataInceput;
    }

    public LocalDate getDataIncheiere() {
        return dataIncheiere;
    }

    public void setDataIncheiere(LocalDate dataIncheiere) {
        this.dataIncheiere = dataIncheiere;
    }

    public String getNumeAsigurator() {
        return numeAsigurator;
    }

    public void setNumeAsigurator(String numeAsigurator) {
        this.numeAsigurator = numeAsigurator;
    }

    public String getVinMasina() {
        return vinMasina;
    }

    public void setVinMasina(String vinMasina) {
        this.vinMasina = vinMasina;
    }

    public String getNumeProprietar() {
        return numeProprietar;
    }

    public void setNumeProprietar(String numeProprietar) {
        this.numeProprietar = numeProprietar;
    }

    public Masina getMasina() {
        return masina;
    }

    public void setMasina(Masina masina) {
        this.masina = masina;
    }


    public boolean esteValida() {
        LocalDate astazi = LocalDate.now();
        return !astazi.isBefore(dataInceput) && !astazi.isAfter(dataIncheiere);
    }
}