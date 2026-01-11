package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.model.Piesa;
import com.carapp.carmaintenance.service.PiesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/piese")
@CrossOrigin(origins = "*")
public class PiesaController {

    @Autowired
    private PiesaService piesaService;

    @GetMapping
    public ResponseEntity<List<Piesa>> getAllPiese() {
        return ResponseEntity.ok(piesaService.getAllPiese());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Piesa> getPiesaById(@PathVariable Long id) {
        return piesaService.getPiesaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cauta/{nume}")
    public ResponseEntity<List<Piesa>> cautaPiesa(@PathVariable String nume) {
        return ResponseEntity.ok(piesaService.cautaPiesaDupaNume(nume));
    }

    @PostMapping
    public ResponseEntity<?> createPiesa(@RequestBody Piesa piesa) {
        try {
            Piesa createdPiesa = piesaService.createPiesa(piesa);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPiesa);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePiesa(@PathVariable Long id, @RequestBody Piesa piesa) {
        try {
            Piesa updatedPiesa = piesaService.updatePiesa(id, piesa);
            return ResponseEntity.ok(updatedPiesa);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePiesa(@PathVariable Long id) {
        try {
            piesaService.deletePiesa(id);
            return ResponseEntity.ok("Piesă ștearsă cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}