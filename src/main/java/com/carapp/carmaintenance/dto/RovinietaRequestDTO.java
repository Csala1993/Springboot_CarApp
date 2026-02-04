package com.carapp.carmaintenance.dto;

import com.carapp.carmaintenance.model.Rovinieta;
import java.time.LocalDate;

public class RovinietaRequestDTO {
    private LocalDate dataInceput;
    private Rovinieta.DurataRovinieta durata;

    public LocalDate getDataInceput() { return dataInceput; }
    public void setDataInceput(LocalDate dataInceput) { this.dataInceput = dataInceput; }

    public Rovinieta.DurataRovinieta getDurata() { return durata; }
    public void setDurata(Rovinieta.DurataRovinieta durata) { this.durata = durata; }
}
