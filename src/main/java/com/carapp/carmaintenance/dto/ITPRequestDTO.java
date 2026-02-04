package com.carapp.carmaintenance.dto;

import java.time.LocalDate;

public class ITPRequestDTO {
    private LocalDate dataEfectuare;

    public LocalDate getDataEfectuare() { return dataEfectuare; }
    public void setDataEfectuare(LocalDate dataEfectuare) { this.dataEfectuare = dataEfectuare; }
}
