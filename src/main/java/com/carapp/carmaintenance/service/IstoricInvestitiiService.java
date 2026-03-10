package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.dto.InvestitieRequestDTO;
import com.carapp.carmaintenance.dto.IstoricInvestitiiSimpleDTO;
import com.carapp.carmaintenance.dto.PiesaMiniDTO;
import com.carapp.carmaintenance.model.IstoricInvestitii;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.model.Piesa;
import com.carapp.carmaintenance.repository.IstoricInvestitiiRepository;
import com.carapp.carmaintenance.repository.MasinaRepository;
import com.carapp.carmaintenance.repository.PiesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

@Service
public class IstoricInvestitiiService {

    @Autowired private IstoricInvestitiiRepository investitiiRepository;
    @Autowired private MasinaRepository masinaRepository;
    @Autowired private PiesaRepository piesaRepository;

    @Transactional
    public IstoricInvestitii adaugaInvestitie(Long masinaId, InvestitieRequestDTO dto) {
        Masina masina = masinaRepository.findById(masinaId)
                .orElseThrow(() -> new RuntimeException("Mașina nu a fost găsită!"));

        if (dto.getDataInvestitie() == null) throw new RuntimeException("dataInvestitie este obligatorie!");
        if (dto.getTitlu() == null || dto.getTitlu().isBlank()) throw new RuntimeException("titlu este obligatoriu!");

        IstoricInvestitii inv = new IstoricInvestitii();
        inv.setDataInvestitie(dto.getDataInvestitie());
        inv.setTitlu(dto.getTitlu());
        inv.setDescriere(dto.getDescriere());
        inv.setManopera(dto.getManopera() != null ? dto.getManopera() : 0.0);

        inv.setMasina(masina);


        if (dto.getPiesaIds() != null && !dto.getPiesaIds().isEmpty()) {
            List<Piesa> piese = piesaRepository.findAllById(dto.getPiesaIds());
            if (piese.size() != dto.getPiesaIds().size()) {
                throw new RuntimeException("Unele piese nu au fost găsite (ID invalid).");
            }
            piese.forEach(inv::adaugaPiesa);
        }

        inv.calculeazaCostTotal();

        return investitiiRepository.save(inv);
    }

    public List<IstoricInvestitiiSimpleDTO> getInvestitiiByMasina(Long masinaId) {
        return investitiiRepository.findByMasinaId(masinaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void stergeInvestitie(Long investitieId) {
        investitiiRepository.deleteById(investitieId);
    }

    private IstoricInvestitiiSimpleDTO toDTO(IstoricInvestitii inv) {
        java.util.Set<PiesaMiniDTO> piese = inv.getPiese().stream()
                .map(p -> new PiesaMiniDTO(p.getId(), p.getNume(), p.getPret()))
                .collect(Collectors.toSet());

        return new IstoricInvestitiiSimpleDTO(
                inv.getId(),
                inv.getDataInvestitie(),
                inv.getTitlu(),
                inv.getDescriere(),
                inv.getManopera(),
                inv.getCostTotal(),
                piese
        );
    }
}
