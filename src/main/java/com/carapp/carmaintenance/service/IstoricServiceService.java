package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.dto.PiesaMiniDTO;
import com.carapp.carmaintenance.dto.IstoricServiceDTO;
import com.carapp.carmaintenance.dto.MasinaDTO;
import com.carapp.carmaintenance.model.IstoricService;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.model.Piesa;
import com.carapp.carmaintenance.repository.IstoricServiceRepository;
import com.carapp.carmaintenance.repository.MasinaRepository;
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
    private MasinaRepository masinaRepository;

    @Autowired
    private PiesaRepository piesaRepository;

    public List<IstoricService> getAllServices() {
        return istoricServiceRepository.findAll();
    }

    public List<IstoricService> getServicesByMasinaId(Long masinaId) {
        return istoricServiceRepository.findByMasinaIdOrderByDataServiceDesc(masinaId);
    }

    public Optional<IstoricService> getServiceById(Long id) {
        return istoricServiceRepository.findById(id);
    }

    @Transactional
    public IstoricService createService(Long masinaId, IstoricService service, List<PiesaRequestDTO> pieseRequest) {
        Masina masina = masinaRepository.findById(masinaId)
                .orElseThrow(() -> new RuntimeException("Mașina nu a fost găsită!"));

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

    public void deleteService(Long id) {
        IstoricService service = istoricServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service-ul nu a fost găsit!"));
        istoricServiceRepository.delete(service);
    }

    public IstoricServiceDTO convertToDTO(IstoricService service) {
        MasinaDTO masinaDTO = new MasinaDTO(
                service.getMasina().getId(),
                service.getMasina().getMarca(),
                service.getMasina().getModel(),
                service.getMasina().getAn(),
                service.getMasina().getNumarInmatriculare(),
                service.getMasina().getVin(),
                service.getMasina().getKilometraj()
        );

        List<PiesaMiniDTO> pieseDTO = service.getPieseSchimbate().stream()
                .map(p -> new PiesaMiniDTO(p.getId(), p.getNume(), p.getPret()))
                .collect(Collectors.toList());

        return new IstoricServiceDTO(
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

    public List<IstoricServiceDTO> getAllServicesDTO() {
        return getAllServices().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<IstoricServiceDTO> getServicesByMasinaIdDTO(Long masinaId) {
        return getServicesByMasinaId(masinaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<IstoricServiceDTO> getServiceByIdDTO(Long id) {
        return getServiceById(id).map(this::convertToDTO);
    }
}