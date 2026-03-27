package com.carapp.carmaintenance.dto;

import com.carapp.carmaintenance.model.Asigurare;
import com.carapp.carmaintenance.model.Rovinieta;
import com.carapp.carmaintenance.model.ITP;


import java.util.List;

public class MasinaDetailDTO {
    private Long id;
    private String marca;
    private String model;
    private Integer an;
    private String numarInmatriculare;
    private String vin;
    private Integer kilometraj;
    private Asigurare asigurare;
    private Rovinieta rovinieta;
    // no dto s insdie other dtos
    private List<IstoricServiceSimpleDTO> istoricService;
    private ITP itp;
    private List<IstoricInvestitiiSimpleDTO> istoricInvestitii;


    // Constructori
    public MasinaDetailDTO() {}

    public MasinaDetailDTO(Long id, String marca, String model, Integer an,
                           String numarInmatriculare, String vin, Integer kilometraj,
                           Asigurare asigurare, Rovinieta rovinieta, ITP itp,
                           List<IstoricServiceSimpleDTO> istoricService) {
        this.id = id;
        this.marca = marca;
        this.model = model;
        this.an = an;
        this.numarInmatriculare = numarInmatriculare;
        this.vin = vin;
        this.kilometraj = kilometraj;
        this.asigurare = asigurare;
        this.rovinieta = rovinieta;
        this.itp = itp;
        this.istoricService = istoricService;
    }

    // Getters și Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Integer getAn() { return an; }
    public void setAn(Integer an) { this.an = an; }

    public String getNumarInmatriculare() { return numarInmatriculare; }
    public void setNumarInmatriculare(String numarInmatriculare) { this.numarInmatriculare = numarInmatriculare; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public Integer getKilometraj() { return kilometraj; }
    public void setKilometraj(Integer kilometraj) { this.kilometraj = kilometraj; }

    public Asigurare getAsigurare() { return asigurare; }
    public void setAsigurare(Asigurare asigurare) { this.asigurare = asigurare; }

    public Rovinieta getRovinieta() { return rovinieta; }
    public void setRovinieta(Rovinieta rovinieta) { this.rovinieta = rovinieta; }

    public List<IstoricServiceSimpleDTO> getIstoricService() { return istoricService; }
    public void setIstoricService(List<IstoricServiceSimpleDTO> istoricService) { this.istoricService = istoricService; }

    public ITP getItp() { return itp; }
    public void setItp(ITP itp) { this.itp = itp; }

    public List<IstoricInvestitiiSimpleDTO> getIstoricInvestitii() { return istoricInvestitii; }
    public void setIstoricInvestitii(List<IstoricInvestitiiSimpleDTO> istoricInvestitii) { this.istoricInvestitii = istoricInvestitii; }

}
