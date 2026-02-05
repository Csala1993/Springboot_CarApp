package com.carapp.carmaintenance.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "masini")
public class Masina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer an;

    @Column(nullable = false, unique = true)
    private String numarInmatriculare;

    @Column
    private String vin;

    @Column
    private Integer kilometraj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "asigurare_id", referencedColumnName = "id")
    private Asigurare asigurare;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rovinieta_id", referencedColumnName = "id")
    private Rovinieta rovinieta;

    @OneToMany(mappedBy = "masina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IstoricService> istoricService = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "itp_id", referencedColumnName = "id")
    private ITP itp;


    // Constructori
    public Masina() {}

    public Masina(String marca, String model, Integer an, String numarInmatriculare) {
        this.marca = marca;
        this.model = model;
        this.an = an;
        this.numarInmatriculare = numarInmatriculare;
    }

    // Getters și Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }

    public String getNumarInmatriculare() {
        return numarInmatriculare;
    }

    public void setNumarInmatriculare(String numarInmatriculare) {
        this.numarInmatriculare = numarInmatriculare;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getKilometraj() {
        return kilometraj;
    }

    public void setKilometraj(Integer kilometraj) {
        this.kilometraj = kilometraj;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Asigurare getAsigurare() {
        return asigurare;
    }

    public void setAsigurare(Asigurare asigurare) {
        this.asigurare = asigurare;
        if (asigurare != null) {
            asigurare.setMasina(this);
        }
    }

    public Rovinieta getRovinieta() {
        return rovinieta;
    }

    public void setRovinieta(Rovinieta rovinieta) {
        this.rovinieta = rovinieta;
        if (rovinieta != null) {
            rovinieta.setMasina(this);
        }
    }

    public List<IstoricService> getIstoricService() {
        return istoricService;
    }

    public void setIstoricService(List<IstoricService> istoricService) {
        this.istoricService = istoricService;
    }

    // Metode helper pentru istoric service
    public void adaugaService(IstoricService service) {
        istoricService.add(service);
        service.setMasina(this);
    }

    public void stergeService(IstoricService service) {
        istoricService.remove(service);
        service.setMasina(null);
    }

    public ITP getItp() {
        return itp;
    }

    public void setItp(ITP itp) {
        this.itp = itp;
        if (itp != null) {
            itp.setMasina(this);
        }
    }

    @OneToMany(mappedBy = "masina", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<IstoricInvestitii> istoricInvestitii = new java.util.ArrayList<>();

    public java.util.List<IstoricInvestitii> getIstoricInvestitii() { return istoricInvestitii; }
    public void setIstoricInvestitii(java.util.List<IstoricInvestitii> istoricInvestitii) { this.istoricInvestitii = istoricInvestitii; }

    public void adaugaInvestitie(IstoricInvestitii inv) {
        istoricInvestitii.add(inv);
        inv.setMasina(this);
    }

    public void stergeInvestitie(IstoricInvestitii inv) {
        istoricInvestitii.remove(inv);
        inv.setMasina(null);
    }


}