package com.carapp.carmaintenance.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nume;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String parola;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Masina> masini = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User() {}

    public User(String nume, String email, String parola) {
        this.nume = nume;
        this.email = email;
        this.parola = parola;
        this.role=Role.USER;
    }


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public List<Masina> getMasini() {
        return masini;
    }

    public void setMasini(List<Masina> masini) {
        this.masini = masini;
    }


    public void adaugaMasina(Masina masina) {
        masini.add(masina);
        masina.setUser(this);
    }

    public void stergeMasina(Masina masina) {
        masini.remove(masina);
        masina.setUser(null);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}