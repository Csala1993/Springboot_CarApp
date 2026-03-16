package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.model.Asigurare;
import com.carapp.carmaintenance.service.AsigurareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asigurari")
@CrossOrigin(origins = "*")
public class AsigurareController {

    @Autowired
    private AsigurareService asigurareService;

    @GetMapping
    public ResponseEntity<List<Asigurare>> getAllAsigurari() {
        return ResponseEntity.ok(asigurareService.getAllAsigurari());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asigurare> getAsigurareById(@PathVariable Long id) {
        return asigurareService.getAsigurareById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vin/{vin}")
    public ResponseEntity<Asigurare> getAsigurareByVin(@PathVariable String vin) {
        return asigurareService.getAsigurareByVin(vin)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/expira/{zile}")
    public ResponseEntity<List<Asigurare>> getAsigurariCareExpira(@PathVariable int zile) {
        return ResponseEntity.ok(asigurareService.getAsigurariCareExpira(zile));
    }

    @PostMapping
    public ResponseEntity<?> createAsigurare(@RequestBody Asigurare asigurare) {
        try {
            Asigurare createdAsigurare = asigurareService.createAsigurare(asigurare);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAsigurare);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/masina/{masinaId}")
    public ResponseEntity<?> createAsigurareForMasina(@PathVariable Long masinaId, @RequestBody Asigurare asigurare) {
        try {
            Asigurare createdAsigurare = asigurareService.createAsigurareForMasina(masinaId, asigurare);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAsigurare);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsigurare(@PathVariable Long id, @RequestBody Asigurare asigurare) {
        try {
            Asigurare updatedAsigurare = asigurareService.updateAsigurare(id, asigurare);
            return ResponseEntity.ok(updatedAsigurare);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAsigurare(@PathVariable Long id) {
        try {
            asigurareService.deleteAsigurare(id);
            return ResponseEntity.ok("Asigurare ștearsă cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}