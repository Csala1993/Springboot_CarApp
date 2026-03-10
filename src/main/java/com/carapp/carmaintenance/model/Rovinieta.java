package com.carapp.carmaintenance.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

@Entity
@Table(name = "roviniete")
public class Rovinieta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataInceput;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DurataRovinieta durata;

    @Column(nullable = false)
    private LocalDate dataExpirare;

    @OneToOne(mappedBy = "rovinieta")
    @JsonIgnore
    private Masina masina;


    public enum DurataRovinieta {
        O_ZI(1),
        TREIZECI_ZILE(30),
        UN_AN(365);

        private final int zile;

        DurataRovinieta(int zile) {
            this.zile = zile;
        }

        public int getZile() {
            return zile;
        }
    }


    public Rovinieta() {}

    public Rovinieta(LocalDate dataInceput, DurataRovinieta durata) {
        this.dataInceput = dataInceput;
        this.durata = durata;
        this.dataExpirare = calculeazaDataExpirare();
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
        // Recalculează data expirare când se schimbă data început
        if (this.durata != null) {
            this.dataExpirare = calculeazaDataExpirare();
        }
    }

    public DurataRovinieta getDurata() {
        return durata;
    }

    public void setDurata(DurataRovinieta durata) {
        this.durata = durata;
        // Recalculează data expirare când se schimbă durata
        if (this.dataInceput != null) {
            this.dataExpirare = calculeazaDataExpirare();
        }
    }

    public LocalDate getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(LocalDate dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public Masina getMasina() {
        return masina;
    }

    public void setMasina(Masina masina) {
        this.masina = masina;
    }


    private LocalDate calculeazaDataExpirare() {
        if (dataInceput == null || durata == null) {
            return null;
        }
        return dataInceput.plusDays(durata.getZile());
    }


    public boolean esteValida() {
        LocalDate astazi = LocalDate.now();
        return !astazi.isBefore(dataInceput) && !astazi.isAfter(dataExpirare);
    }


    public long zileRamase() {
        LocalDate astazi = LocalDate.now();
        if (astazi.isAfter(dataExpirare)) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(astazi, dataExpirare);
    }
}