package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.dto.PiesaResponseDTO;
import com.carapp.carmaintenance.dto.IstoricServiceResponseDTO;
import com.carapp.carmaintenance.dto.MasinaDTO;
import com.carapp.carmaintenance.model.IstoricService;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.model.Piesa;
import com.carapp.carmaintenance.repository.IstoricServiceRepository;
import com.carapp.carmaintenance.repository.PiesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.carapp.carmaintenance.dto.PiesaRequestDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IstoricServiceService {

    @Autowired
    private IstoricServiceRepository istoricServiceRepository;

    @Autowired
    private PiesaRepository piesaRepository;

    @Autowired
    private MasinaService masinaService;

    public List<IstoricService> getAllServices() {

        return istoricServiceRepository.findAll();
    }

    public List<IstoricService> getServicesByMasinaId(Long masinaId) {
        masinaService.getMasinaCurenta(masinaId);

        return istoricServiceRepository
                .findByMasinaIdOrderByDataServiceDesc(masinaId);
    }

    @Transactional(readOnly = true)
    public Optional<IstoricService> getServiceById(Long id) {
        Optional<IstoricService> service =
                istoricServiceRepository.findById(id);

        service.ifPresent(item ->
                masinaService.getMasinaCurenta(
                        item.getMasina().getId()
                )
        );

        return service;
    }

    @Transactional
    public IstoricService createService(Long masinaId, IstoricService service, List<PiesaRequestDTO> pieseRequest) {

        Masina masina = masinaService.getMasinaCurenta(masinaId);

        service.setMasina(masina);

        if (service.getKilometrajLaService() != null &&
                (masina.getKilometraj() == null || service.getKilometrajLaService() > masina.getKilometraj())) {
            masina.setKilometraj(service.getKilometrajLaService());
        }


        if (pieseRequest != null && !pieseRequest.isEmpty()) {
            for (PiesaRequestDTO dto : pieseRequest) {
                Piesa piesa = piesaRepository
                        .findByNumeIgnoreCaseAndDistribuitor(dto.getNume(), dto.getDistribuitor())
                        .orElseGet(() -> {
                            Piesa nouaPiesa = new Piesa();
                            nouaPiesa.setNume(dto.getNume());
                            nouaPiesa.setPret(dto.getPret());
                            nouaPiesa.setDistribuitor(dto.getDistribuitor());
                            return piesaRepository.save(nouaPiesa);
                        });
                service.adaugaPiesa(piesa);
            }
        }

        service.calculeazaCostTotal();
        return istoricServiceRepository.save(service);
    }

    @Transactional
    public IstoricService updateService(Long id, IstoricService serviceDetails, List<PiesaRequestDTO> pieseRequest) {
        IstoricService service = istoricServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service-ul nu a fost găsit!"));

        masinaService.getMasinaCurenta(service.getMasina().getId());

        service.setDataService(serviceDetails.getDataService());
        service.setKilometrajLaService(serviceDetails.getKilometrajLaService());

        Masina masina = service.getMasina();
        Integer kmNou = serviceDetails.getKilometrajLaService();

        if (kmNou != null && (masina.getKilometraj() == null || kmNou > masina.getKilometraj())) {
            masina.setKilometraj(kmNou);
        }

        service.setDescriere(serviceDetails.getDescriere());
        service.setServiceAuto(serviceDetails.getServiceAuto());
        service.setManopera(serviceDetails.getManopera());

        if (pieseRequest != null) {
            service.getPieseSchimbate().clear();
            for (PiesaRequestDTO p : pieseRequest) {
                Piesa piesa = piesaRepository
                        .findByNumeIgnoreCaseAndDistribuitor(p.getNume(), p.getDistribuitor())
                        .orElseGet(() -> {
                            Piesa nouaPiesa = new Piesa();
                            nouaPiesa.setNume(p.getNume());
                            nouaPiesa.setPret(p.getPret());
                            nouaPiesa.setDistribuitor(p.getDistribuitor());
                            return piesaRepository.save(nouaPiesa);
                        });
                service.adaugaPiesa(piesa);
            }
        }

        service.calculeazaCostTotal();
        return istoricServiceRepository.save(service);
    }

    @Transactional
    public void deleteService(Long id) {
        IstoricService service = istoricServiceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Service-ul nu a fost gasit!")
                );

        masinaService.getMasinaCurenta(service.getMasina().getId());

        istoricServiceRepository.delete(service);
    }

    public IstoricServiceResponseDTO convertToDTO(IstoricService service) {
        MasinaDTO masinaDTO = new MasinaDTO(
                service.getMasina().getId(),
                service.getMasina().getMarca(),
                service.getMasina().getModel(),
                service.getMasina().getAn(),
                service.getMasina().getNumarInmatriculare(),
                service.getMasina().getVin(),
                service.getMasina().getKilometraj()
        );

        List<PiesaResponseDTO> pieseDTO = service.getPieseSchimbate().stream()
                .map(p -> new PiesaResponseDTO(p.getId(), p.getNume(), p.getPret()))
                .collect(Collectors.toList());

        return new IstoricServiceResponseDTO(
                service.getId(),
                service.getDataService(),
                service.getKilometrajLaService(),
                service.getDescriere(),
                service.getServiceAuto(),
                service.getCostTotal(),
                service.getManopera(), // ADAUGĂ
                masinaDTO,
                pieseDTO
        );
    }

    public List<IstoricServiceResponseDTO> getAllServicesDTO() {
        return getAllServices().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<IstoricServiceResponseDTO> getServicesByMasinaIdDTO(Long masinaId) {
        return getServicesByMasinaId(masinaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<IstoricServiceResponseDTO> getServiceByIdDTO(Long id) {
        return getServiceById(id).map(this::convertToDTO);
    }
}