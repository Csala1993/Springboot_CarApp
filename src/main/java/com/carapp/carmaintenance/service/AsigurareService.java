package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.model.Asigurare;
import com.carapp.carmaintenance.repository.AsigurareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsigurareService {

    @Autowired
    private AsigurareRepository asigurareRepository;

    public List<Asigurare> getAllAsigurari() {
        return asigurareRepository.findAll();
    }

    public Optional<Asigurare> getAsigurareById(Long id) {
        return asigurareRepository.findById(id);
    }

    public Optional<Asigurare> getAsigurareByVin(String vin) {
        return asigurareRepository.findByVinMasina(vin);
    }

    public Asigurare createAsigurare(Asigurare asigurare) {
        // Validare date
        if (asigurare.getDataInceput().isAfter(asigurare.getDataIncheiere())) {
            throw new RuntimeException("Data de început nu poate fi după data de încheiere!");
        }
        return asigurareRepository.save(asigurare);
    }

    public Asigurare updateAsigurare(Long id, Asigurare asigurareDetails) {
        Asigurare asigurare = asigurareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asigurarea nu a fost găsită!"));

        if (asigurareDetails.getDataInceput().isAfter(asigurareDetails.getDataIncheiere())) {
            throw new RuntimeException("Data de început nu poate fi după data de încheiere!");
        }

        asigurare.setDataInceput(asigurareDetails.getDataInceput());
        asigurare.setDataIncheiere(asigurareDetails.getDataIncheiere());
        asigurare.setNumeAsigurator(asigurareDetails.getNumeAsigurator());
        asigurare.setVinMasina(asigurareDetails.getVinMasina());
        asigurare.setNumeProprietar(asigurareDetails.getNumeProprietar());

        return asigurareRepository.save(asigurare);
    }

    public void deleteAsigurare(Long id) {
        Asigurare asigurare = asigurareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asigurarea nu a fost găsită!"));
        asigurareRepository.delete(asigurare);
    }

    // Găsește asigurările care expiră în următoarele X zile
    public List<Asigurare> getAsigurariCareExpira(int zile) {
        LocalDate astazi = LocalDate.now();
        LocalDate dataViitoare = astazi.plusDays(zile);
        return asigurareRepository.findByDataIncheiereBeforeAndDataIncheiereAfter(dataViitoare, astazi);
    }
}