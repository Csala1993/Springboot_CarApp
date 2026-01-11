package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.model.Piesa;
import com.carapp.carmaintenance.repository.PiesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PiesaService {

    @Autowired
    private PiesaRepository piesaRepository;

    public List<Piesa> getAllPiese() {
        return piesaRepository.findAll();
    }

    public Optional<Piesa> getPiesaById(Long id) {
        return piesaRepository.findById(id);
    }

    public List<Piesa> cautaPiesaDupaNume(String nume) {
        return piesaRepository.findByNumeContainingIgnoreCase(nume);
    }

    public Piesa createPiesa(Piesa piesa) {
        if (piesa.getPret() == null || piesa.getPret() < 0) {
            throw new RuntimeException("Prețul piesei trebuie să fie valid!");
        }
        return piesaRepository.save(piesa);
    }

    public Piesa updatePiesa(Long id, Piesa piesaDetails) {
        Piesa piesa = piesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piesa nu a fost găsită!"));

        piesa.setNume(piesaDetails.getNume());
        piesa.setPret(piesaDetails.getPret());
        piesa.setDistribuitor(piesaDetails.getDistribuitor());

        return piesaRepository.save(piesa);
    }

    public void deletePiesa(Long id) {
        Piesa piesa = piesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piesa nu a fost găsită!"));
        piesaRepository.delete(piesa);
    }
}