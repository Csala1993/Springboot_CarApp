package com.carapp.carmaintenance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "istoric_investitii")
public class IstoricInvestitii {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataInvestitie;

    @Column(nullable = false)
    private String titlu; // ex: "Jante OZ 18''"

    @Column(length = 2000)
    private String descriere;

    @Column(nullable = false)
    private Double costTotal = 0.0; // se calculează din piese (+ opțional manopera)

    @Column
    private Double manopera = 0.0; // opțional (dacă vrei)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masina_id", nullable = false)
    @JsonIgnore
    private Masina masina;

    // ✅ Refolosești Piesa exact ca la IstoricService
    @ManyToMany
    @JoinTable(
            name = "investitie_piese",
            joinColumns = @JoinColumn(name = "investitie_id"),
            inverseJoinColumns = @JoinColumn(name = "piesa_id")
    )
    private Set<Piesa> piese = new HashSet<>();

    public IstoricInvestitii() {}

    // --- getters/setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataInvestitie() { return dataInvestitie; }
    public void setDataInvestitie(LocalDate dataInvestitie) { this.dataInvestitie = dataInvestitie; }

    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }

    public Double getCostTotal() { return costTotal; }
    public void setCostTotal(Double costTotal) { this.costTotal = costTotal; }

    public Double getManopera() { return manopera; }
    public void setManopera(Double manopera) { this.manopera = manopera; }

    public Masina getMasina() { return masina; }
    public void setMasina(Masina masina) { this.masina = masina; }

    public Set<Piesa> getPiese() { return piese; }
    public void setPiese(Set<Piesa> piese) { this.piese = piese; }

    // --- helper methods ---
    public void adaugaPiesa(Piesa piesa) {
        this.piese.add(piesa);
    }

    public void stergePiesa(Piesa piesa) {
        this.piese.remove(piesa);
    }

    // ✅ cost total = sum(piese.pret) + manopera
    public void calculeazaCostTotal() {
        double sumaPiese = this.piese.stream()
                .mapToDouble(p -> p.getPret() != null ? p.getPret() : 0.0)
                .sum();
        double man = this.manopera != null ? this.manopera : 0.0;
        this.costTotal = sumaPiese + man;
    }
}
