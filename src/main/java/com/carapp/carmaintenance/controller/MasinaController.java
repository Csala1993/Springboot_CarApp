package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.dto.MasinaDetailDTO;
import com.carapp.carmaintenance.dto.RovinietaRequestDTO;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.service.MasinaService;
import com.carapp.carmaintenance.model.Asigurare;
import com.carapp.carmaintenance.service.AsigurareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.carapp.carmaintenance.dto.ITPRequestDTO;
import com.carapp.carmaintenance.dto.MasinaListDTO;


import java.util.List;

@RestController
@RequestMapping("/api/masini")
@CrossOrigin(origins = "*")
public class MasinaController {

    @Autowired
    private MasinaService masinaService;
    @Autowired
    private AsigurareService asigurareService;

    @GetMapping
    public ResponseEntity<List<MasinaListDTO>> getAllMasini() {
        return ResponseEntity.ok(masinaService.getAllMasiniListDTO());
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

    @GetMapping("/{id}/rovinieta")
    public ResponseEntity<?> getRovinieta(@PathVariable Long id) {
        return masinaService.getMasinaById(id)
                .map(masina -> masina.getRovinieta() != null
                        ? ResponseEntity.ok(masina.getRovinieta())
                        : ResponseEntity.notFound().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/itp")
    public ResponseEntity<?> getItp(@PathVariable Long id) {
        return masinaService.getMasinaById(id)
                .map(masina -> masina.getItp() != null
                        ? ResponseEntity.ok(masina.getItp())
                        : ResponseEntity.notFound().build())
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/asigurare")
    public ResponseEntity<?> getAsigurare(@PathVariable Long id) {
        return masinaService.getMasinaById(id)
                .map(masina -> masina.getAsigurare() != null
                        ? ResponseEntity.ok(masina.getAsigurare())
                        : ResponseEntity.notFound().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/asigurare")
    public ResponseEntity<?> createAsigurare(@PathVariable Long id, @RequestBody Asigurare asigurare) {
        try {
            Asigurare created = asigurareService.createAsigurareForMasina(id, asigurare);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @PostMapping("/{id}/itp")
    public ResponseEntity<?> adaugaItp(@PathVariable Long id, @RequestBody ITPRequestDTO dto) {
        try {
            Masina updated = masinaService.adaugaItpLaMasina(id, dto.getDataEfectuare());
            return ResponseEntity.status(HttpStatus.CREATED).body(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}