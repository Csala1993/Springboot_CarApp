package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.dto.MasinaDetailDTO;
import com.carapp.carmaintenance.dto.RovinietaRequestDTO;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.service.MasinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/masini")
@CrossOrigin(origins = "*")
public class MasinaController {

    @Autowired
    private MasinaService masinaService;

    @GetMapping
    public ResponseEntity<List<MasinaDetailDTO>> getAllMasini() {
        return ResponseEntity.ok(masinaService.getAllMasiniDTO());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MasinaDetailDTO>> getMasiniByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(masinaService.getMasiniByUserIdDTO(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasinaDetailDTO> getMasinaById(@PathVariable Long id) {
        return masinaService.getMasinaByIdDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createMasina(@PathVariable Long userId, @RequestBody Masina masina) {
        try {
            Masina createdMasina = masinaService.createMasina(userId, masina);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMasina);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMasina(@PathVariable Long id, @RequestBody Masina masina) {
        try {
            Masina updatedMasina = masinaService.updateMasina(id, masina);
            return ResponseEntity.ok(updatedMasina);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMasina(@PathVariable Long id) {
        try {
            masinaService.deleteMasina(id);
            return ResponseEntity.ok("Mașină ștearsă cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/rovinieta")
    public ResponseEntity<?> adaugaRovinieta(@PathVariable Long id, @RequestBody RovinietaRequestDTO dto) {
        try {
            Masina updated = masinaService.adaugaRovinietaLaMasina(id, dto.getDataInceput(), dto.getDurata());
            return ResponseEntity.status(HttpStatus.CREATED).body(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}