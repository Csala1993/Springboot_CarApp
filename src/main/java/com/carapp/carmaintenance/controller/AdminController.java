package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MasinaRepository masinaRepository;

    @Autowired
    private AsigurareRepository asigurareRepository;

    @Autowired
    private PiesaRepository piesaRepository;

    @Autowired
    private IstoricServiceRepository istoricServiceRepository;

    @DeleteMapping("/reset-database")
    public ResponseEntity<String> resetDatabase() {
        try {
            // Șterge toate datele în ordine (pentru a respecta foreign keys)
            istoricServiceRepository.deleteAll();
            piesaRepository.deleteAll();
            masinaRepository.deleteAll();
            asigurareRepository.deleteAll();
            userRepository.deleteAll();

            return ResponseEntity.ok(
                    "Baza de date a fost golit cu succes!\n" +
                            "Repornește aplicația pentru a recrea datele de test."
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare: " + e.getMessage());
        }
    }

    @GetMapping("/database-info")
    public ResponseEntity<String> getDatabaseInfo() {
        String info = "===========================================\n" +
                "Informații Bază de Date:\n" +
                "- " + userRepository.count() + " utilizatori\n" +
                "- " + masinaRepository.count() + " mașini\n" +
                "- " + asigurareRepository.count() + " asigurări\n" +
                "- " + piesaRepository.count() + " piese\n" +
                "- " + istoricServiceRepository.count() + " servicii efectuate\n" +
                "===========================================";
        return ResponseEntity.ok(info);
    }
}