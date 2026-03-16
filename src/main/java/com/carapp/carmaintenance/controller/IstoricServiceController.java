package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.dto.IstoricServiceDTO;
import com.carapp.carmaintenance.dto.IstoricServiceRequestDTO;
import com.carapp.carmaintenance.model.IstoricService;
import com.carapp.carmaintenance.service.IstoricServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
@CrossOrigin(origins = "*")
public class IstoricServiceController {

    @Autowired
    private IstoricServiceService istoricServiceService;

    @GetMapping
    public ResponseEntity<List<IstoricServiceDTO>> getAllServices() {
        return ResponseEntity.ok(istoricServiceService.getAllServicesDTO());
    }

    @GetMapping("/masina/{masinaId}")
    public ResponseEntity<List<IstoricServiceDTO>> getServicesByMasina(@PathVariable Long masinaId) {
        return ResponseEntity.ok(istoricServiceService.getServicesByMasinaIdDTO(masinaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IstoricServiceDTO> getServiceById(@PathVariable Long id) {
        return istoricServiceService.getServiceByIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/masina/{masinaId}")
    public ResponseEntity<?> createService(
            @PathVariable Long masinaId,
            @RequestBody IstoricServiceRequestDTO dto) {
        try {
            IstoricService service = new IstoricService();
            service.setDataService(dto.getDataService());
            service.setKilometrajLaService(dto.getKilometrajLaService());
            service.setDescriere(dto.getDescriere());
            service.setServiceAuto(dto.getServiceAuto());
            service.setManopera(dto.getManopera()); // ADĂUGAT

            IstoricService createdService = istoricServiceService.createService(
                    masinaId, service, dto.getPieseIds());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(
            @PathVariable Long id,
            @RequestBody IstoricServiceRequestDTO dto) {
        try {
            IstoricService service = new IstoricService();
            service.setDataService(dto.getDataService());
            service.setKilometrajLaService(dto.getKilometrajLaService());
            service.setDescriere(dto.getDescriere());
            service.setServiceAuto(dto.getServiceAuto());
            service.setManopera(dto.getManopera()); // ADĂUGAT

            IstoricService updatedService = istoricServiceService.updateService(
                    id, service, dto.getPieseIds());
            return ResponseEntity.ok(updatedService);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        try {
            istoricServiceService.deleteService(id);
            return ResponseEntity.ok("Service șters cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}