package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.model.Rovinieta;
import com.carapp.carmaintenance.repository.RovinietaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RovinietaService {

    @Autowired
    private RovinietaRepository rovinietaRepository;

    public List<Rovinieta> getAllRoviniete() {
        return rovinietaRepository.findAll();
    }

    public Optional<Rovinieta> getRovinietaById(Long id) {
        return rovinietaRepository.findById(id);
    }

    public Rovinieta createRovinieta(Rovinieta rovinieta) {
        // Validare: data început nu poate fi în trecut
        if (rovinieta.getDataInceput().isBefore(LocalDate.now())) {
            throw new RuntimeException("Data început nu poate fi în trecut!");
        }

        // Data expirare se calculează automat în constructor/setter
        return rovinietaRepository.save(rovinieta);
    }

    public Rovinieta updateRovinieta(Long id, Rovinieta rovinietaDetails) {
        Rovinieta rovinieta = rovinietaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rovinieta nu a fost găsită!"));

        rovinieta.setDataInceput(rovinietaDetails.getDataInceput());
        rovinieta.setDurata(rovinietaDetails.getDurata());
        // Data expirare se recalculează automat

        return rovinietaRepository.save(rovinieta);
    }

    public void deleteRovinieta(Long id) {
        Rovinieta rovinieta = rovinietaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rovinieta nu a fost găsită!"));
        rovinietaRepository.delete(rovinieta);
    }

    // Găsește roviniete care expiră în următoarele X zile
    public List<Rovinieta> getRovinieteCareExpira(int zile) {
        LocalDate astazi = LocalDate.now();
        LocalDate dataViitoare = astazi.plusDays(zile);
        return rovinietaRepository.findByDataExpirareBeforeAndDataExpirareAfter(dataViitoare, astazi);
    }

    // Găsește roviniete expirate
    public List<Rovinieta> getRovinieteExpirate() {
        return rovinietaRepository.findByDataExpirareBefore(LocalDate.now());
    }
}