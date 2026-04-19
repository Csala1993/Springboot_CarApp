package com.carapp.carmaintenance.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificari")
public class Notificare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String titlu;

    @Column(nullable = false)
    private String mesaj;

    @Column(nullable = false)
    private LocalDateTime dataCreare;

    @Column(nullable = false)
    private boolean citita = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipNotificare tip;

    public enum TipNotificare {
        ITP, ROVINIETA, ASIGURARE
    }

    public Notificare() {}

    public Notificare(User user, String titlu, String mesaj, TipNotificare tip) {
        this.user = user;
        this.titlu = titlu;
        this.mesaj = mesaj;
        this.tip = tip;
        this.dataCreare = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }
    public String getMesaj() { return mesaj; }
    public void setMesaj(String mesaj) { this.mesaj = mesaj; }
    public LocalDateTime getDataCreare() { return dataCreare; }
    public void setDataCreare(LocalDateTime dataCreare) { this.dataCreare = dataCreare; }
    public boolean isCitita() { return citita; }
    public void setCitita(boolean citita) { this.citita = citita; }
    public TipNotificare getTip() { return tip; }
    public void setTip(TipNotificare tip) { this.tip = tip; }
}