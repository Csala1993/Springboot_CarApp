package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.dto.InvestitieRequestDTO;
import com.carapp.carmaintenance.dto.IstoricInvestitiiResponseDTO;
import com.carapp.carmaintenance.dto.PiesaResponseDTO;
import com.carapp.carmaintenance.model.IstoricInvestitii;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.model.Piesa;
import com.carapp.carmaintenance.repository.IstoricInvestitiiRepository;
import com.carapp.carmaintenance.repository.PiesaRepository;
import com.carapp.carmaintenance.dto.PiesaRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IstoricInvestitiiService {

    @Autowired
    private IstoricInvestitiiRepository investitiiRepository;

    @Autowired
    private PiesaRepository piesaRepository;

    @Autowired
    private MasinaService masinaService;

    @Transactional
    public IstoricInvestitii adaugaInvestitie(Long masinaId, InvestitieRequestDTO dto) {

        Masina masina = masinaService.getMasinaCurenta(masinaId);

        if (dto.getDataInvestitie() == null) throw new RuntimeException("dataInvestitie este obligatorie!");
        if (dto.getTitlu() == null || dto.getTitlu().isBlank()) throw new RuntimeException("titlu este obligatoriu!");

        IstoricInvestitii inv = new IstoricInvestitii();
        inv.setDataInvestitie(dto.getDataInvestitie());
        inv.setTitlu(dto.getTitlu());
        inv.setDescriere(dto.getDescriere());
        inv.setManopera(dto.getManopera() != null ? dto.getManopera() : 0.0);

        inv.setKilometrajLaInvestitie(dto.getKilometrajLaInvestitie());

        if (dto.getKilometrajLaInvestitie() != null &&
                (masina.getKilometraj() == null || dto.getKilometrajLaInvestitie() > masina.getKilometraj())) {
            masina.setKilometraj(dto.getKilometrajLaInvestitie());
        }

        inv.setMasina(masina);


        if (dto.getPiese() != null && !dto.getPiese().isEmpty()) {
            for (PiesaRequestDTO p : dto.getPiese()) {
                Piesa piesa = piesaRepository
                        .findByNumeIgnoreCaseAndDistribuitor(p.getNume(), p.getDistribuitor())
                        .orElseGet(() -> {
                            Piesa nouaPiesa = new Piesa();
                            nouaPiesa.setNume(p.getNume());
                            nouaPiesa.setPret(p.getPret());
                            nouaPiesa.setDistribuitor(p.getDistribuitor());
                            return piesaRepository.save(nouaPiesa);
                        });
                inv.adaugaPiesa(piesa);
            }
        }

        inv.calculeazaCostTotal();

        return investitiiRepository.save(inv);
    }

    @Transactional(readOnly = true)
    public List<IstoricInvestitiiResponseDTO> getInvestitiiByMasina(Long masinaId) {
        masinaService.getMasinaCurenta(masinaId);

        return investitiiRepository.findByMasinaId(masinaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void stergeInvestitie(Long investitieId) {
        IstoricInvestitii investitie = investitiiRepository.findById(investitieId)
                .orElseThrow(() ->
                        new RuntimeException("Investitia nu a fost gasita.")
                );

        masinaService.getMasinaCurenta(
                investitie.getMasina().getId()
        );

        investitiiRepository.delete(investitie);
    }

    private IstoricInvestitiiResponseDTO toDTO(IstoricInvestitii inv) {
        java.util.Set<PiesaResponseDTO> piese = inv.getPiese().stream()
                .map(p -> new PiesaResponseDTO(p.getId(), p.getNume(), p.getPret()))
                .collect(Collectors.toSet());

        return new IstoricInvestitiiResponseDTO(
                inv.getId(),
                inv.getDataInvestitie(),
                inv.getTitlu(),
                inv.getDescriere(),
                inv.getCostTotal(),
                inv.getManopera(),
                inv.getKilometrajLaInvestitie(),
                piese
        );

    }
}
