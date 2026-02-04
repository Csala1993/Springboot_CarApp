package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.model.Rovinieta;
import com.carapp.carmaintenance.service.RovinietaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roviniete")
@CrossOrigin(origins = "*")
public class RovinietaController {

    @Autowired
    private RovinietaService rovinietaService;

    @GetMapping
    public ResponseEntity<List<Rovinieta>> getAllRoviniete() {
        return ResponseEntity.ok(rovinietaService.getAllRoviniete());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rovinieta> getRovinietaById(@PathVariable Long id) {
        return rovinietaService.getRovinietaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/expira/{zile}")
    public ResponseEntity<List<Rovinieta>> getRovinieteCareExpira(@PathVariable int zile) {
        return ResponseEntity.ok(rovinietaService.getRovinieteCareExpira(zile));
    }

    @GetMapping("/expirate")
    public ResponseEntity<List<Rovinieta>> getRovinieteExpirate() {
        return ResponseEntity.ok(rovinietaService.getRovinieteExpirate());
    }

    @PostMapping
    public ResponseEntity<?> createRovinieta(@RequestBody Rovinieta rovinieta) {
        try {
            Rovinieta createdRovinieta = rovinietaService.createRovinieta(rovinieta);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRovinieta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRovinieta(@PathVariable Long id, @RequestBody Rovinieta rovinieta) {
        try {
            Rovinieta updatedRovinieta = rovinietaService.updateRovinieta(id, rovinieta);
            return ResponseEntity.ok(updatedRovinieta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRovinieta(@PathVariable Long id) {
        try {
            rovinietaService.deleteRovinieta(id);
            return ResponseEntity.ok("Rovinieta ștearsă cu succes!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}