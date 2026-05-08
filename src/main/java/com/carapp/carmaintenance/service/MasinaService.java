package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.dto.IstoricServiceSimpleDTO;
import com.carapp.carmaintenance.dto.MasinaDetailDTO;
import com.carapp.carmaintenance.model.ITP;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.model.Rovinieta;
import com.carapp.carmaintenance.model.User;
import com.carapp.carmaintenance.repository.MasinaRepository;
import com.carapp.carmaintenance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.carapp.carmaintenance.dto.MasinaListDTO;
import com.carapp.carmaintenance.dto.IstoricInvestitiiSimpleDTO;
import com.carapp.carmaintenance.dto.PiesaMiniDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MasinaService {

    @Autowired
    private MasinaRepository masinaRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Masina> getAllMasini() {
        return masinaRepository.findAll();
    }

    public List<Masina> getMasiniByUserId(Long userId) {
        return masinaRepository.findByUserId(userId);
    }

    public Optional<Masina> getMasinaById(Long id) {
        return masinaRepository.findById(id);
    }

    public List<MasinaDetailDTO> getMasiniByUserIdDTO(Long userId) {
        return getMasiniByUserId(userId).stream()
                .map(this::convertToDetailDTO)
                .collect(Collectors.toList());
    }

    public Masina createMasina(Long userId, Masina masina) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit!"));

        if (masinaRepository.existsByNumarInmatriculare(masina.getNumarInmatriculare())) {
            throw new RuntimeException("O mașină cu acest număr de înmatriculare există deja!");
        }

        masina.setUser(user);

        if (masina.getAsigurare() != null) {
            masina.getAsigurare().setMasina(masina);
        }
        if (masina.getRovinieta() != null) {
            masina.getRovinieta().setMasina(masina);
        }
        if (masina.getItp() != null) {
            masina.getItp().setMasina(masina);
        }

        return masinaRepository.save(masina);
    }

    public Masina updateMasina(Long id, Masina masinaDetails) {
        Masina masina = masinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mașina nu a fost găsită!"));

        masina.setMarca(masinaDetails.getMarca());
        masina.setModel(masinaDetails.getModel());
        masina.setAn(masinaDetails.getAn());
        masina.setNumarInmatriculare(masinaDetails.getNumarInmatriculare());
        masina.setVin(masinaDetails.getVin());
        masina.setKilometraj(masinaDetails.getKilometraj());

        if (masinaDetails.getAsigurare() != null) {
            masina.setAsigurare(masinaDetails.getAsigurare());
        }
        if (masinaDetails.getRovinieta() != null) {
            masina.setRovinieta(masinaDetails.getRovinieta());
        }
        if (masinaDetails.getItp() != null) {
            masina.setItp(masinaDetails.getItp());
        }

        return masinaRepository.save(masina);
    }

    public void deleteMasina(Long id) {
        Masina masina = masinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mașina nu a fost găsită!"));
        masinaRepository.delete(masina);
    }

    public MasinaDetailDTO convertToDetailDTO(Masina masina) {
        List<IstoricServiceSimpleDTO> istoricDTO = masina.getIstoricService().stream()
                .map(service -> new IstoricServiceSimpleDTO(
                        service.getId(),
                        service.getDataService(),
                        service.getKilometrajLaService(),
                        service.getDescriere(),
                        service.getServiceAuto(),
                        service.getCostTotal(),
                        service.getManopera(), // ADĂUGAT
                        service.getPieseSchimbate().stream()
                                .map(p -> new PiesaMiniDTO(p.getId(), p.getNume(), p.getPret()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        List<IstoricInvestitiiSimpleDTO> investitiiDTO = masina.getIstoricInvestitii().stream()
                .map(inv -> new IstoricInvestitiiSimpleDTO(
                        inv.getId(),
                        inv.getDataInvestitie(),
                        inv.getTitlu(),
                        inv.getDescriere(),
                        inv.getCostTotal(),
                        inv.getManopera(),
                        inv.getKilometrajLaInvestitie(),
                        inv.getPiese().stream()
                                .map(p -> new PiesaMiniDTO(p.getId(), p.getNume(), p.getPret()))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());


        MasinaDetailDTO dto = new MasinaDetailDTO(
                masina.getId(),
                masina.getMarca(),
                masina.getModel(),
                masina.getAn(),
                masina.getNumarInmatriculare(),
                masina.getVin(),
                masina.getKilometraj(),
                masina.getAsigurare(),
                masina.getRovinieta(),
                masina.getItp(),
                istoricDTO
        );

        dto.setIstoricInvestitii(investitiiDTO);
        return dto;
    }

    public Optional<MasinaDetailDTO> getMasinaByIdDTO(Long id) {
        return getMasinaById(id).map(this::convertToDetailDTO);
    }

    @Transactional
    public Masina adaugaRovinietaLaMasina(Long masinaId, LocalDate dataInceput, Rovinieta.DurataRovinieta durata) {
        Masina masina = masinaRepository.findById(masinaId)
                .orElseThrow(() -> new RuntimeException("Mașina nu a fost găsită!"));

        if (dataInceput == null || durata == null) {
            throw new RuntimeException("dataInceput și durata sunt obligatorii!");
        }

        Rovinieta rovinieta = new Rovinieta(dataInceput, durata);
        masina.setRovinieta(rovinieta);

        return masinaRepository.save(masina);
    }

    @Transactional
    public Masina adaugaItpLaMasina(Long masinaId, LocalDate dataEfectuare) {
        Masina masina = masinaRepository.findById(masinaId)
                .orElseThrow(() -> new RuntimeException("Mașina nu a fost găsită!"));

        if (dataEfectuare == null) {
            throw new RuntimeException("dataEfectuare este obligatorie!");
        }

        int varsta = LocalDate.now().getYear() - masina.getAn();
        int aniValabilitate;

        if (varsta < 3) aniValabilitate = 3;
        else if (varsta <= 12) aniValabilitate = 2;
        else aniValabilitate = 1;

        ITP itp = new ITP(dataEfectuare);
        itp.setDataExpirare(dataEfectuare.plusYears(aniValabilitate));

        masina.setItp(itp);
        return masinaRepository.save(masina);
    }

    public MasinaListDTO convertToListDTO(Masina masina) {
        return new MasinaListDTO(
                masina.getId(),
                masina.getAn(),
                masina.getMarca(),
                masina.getVin(),
                masina.getUser().getId(),
                masina.getUser().getNume()
        );
    }

    public List<MasinaListDTO> getAllMasiniListDTO() {
        return masinaRepository.findAll().stream()
                .map(this::convertToListDTO)
                .toList();
    }
}