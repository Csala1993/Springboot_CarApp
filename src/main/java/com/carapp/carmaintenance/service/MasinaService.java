package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.dto.IstoricServiceSimpleDTO;
import com.carapp.carmaintenance.dto.MasinaDetailDTO;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.model.User;
import com.carapp.carmaintenance.repository.MasinaRepository;
import com.carapp.carmaintenance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Masina createMasina(Long userId, Masina masina) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit!"));

        if (masinaRepository.existsByNumarInmatriculare(masina.getNumarInmatriculare())) {
            throw new RuntimeException("O mașină cu acest număr de înmatriculare există deja!");
        }

        masina.setUser(user);

        // Setează relația bidirectională pentru asigurare dacă există
        if (masina.getAsigurare() != null) {
            masina.getAsigurare().setMasina(masina);
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

        // Actualizează asigurarea dacă este furnizată
        if (masinaDetails.getAsigurare() != null) {
            masina.setAsigurare(masinaDetails.getAsigurare());
        }

        return masinaRepository.save(masina);
    }

    public void deleteMasina(Long id) {
        Masina masina = masinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mașina nu a fost găsită!"));
        masinaRepository.delete(masina);
    }

    // Conversie la DTO cu istoric service
    public MasinaDetailDTO convertToDetailDTO(Masina masina) {
        List<IstoricServiceSimpleDTO> istoricDTO = masina.getIstoricService().stream()
                .map(service -> new IstoricServiceSimpleDTO(
                        service.getId(),
                        service.getDataService(),
                        service.getKilometrajLaService(),
                        service.getDescriere(),
                        service.getServiceAuto(),
                        service.getCostTotal(),
                        service.getPieseSchimbate()
                ))
                .collect(Collectors.toList());

        return new MasinaDetailDTO(
                masina.getId(),
                masina.getMarca(),
                masina.getModel(),
                masina.getAn(),
                masina.getNumarInmatriculare(),
                masina.getVin(),
                masina.getKilometraj(),
                masina.getAsigurare(),
                istoricDTO
        );
    }

    public List<MasinaDetailDTO> getAllMasiniDTO() {
        return getAllMasini().stream()
                .map(this::convertToDetailDTO)
                .collect(Collectors.toList());
    }

    public List<MasinaDetailDTO> getMasiniByUserIdDTO(Long userId) {
        return getMasiniByUserId(userId).stream()
                .map(this::convertToDetailDTO)
                .collect(Collectors.toList());
    }

    public Optional<MasinaDetailDTO> getMasinaByIdDTO(Long id) {
        return getMasinaById(id).map(this::convertToDetailDTO);
    }
}