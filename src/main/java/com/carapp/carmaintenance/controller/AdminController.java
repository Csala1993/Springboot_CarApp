package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @DeleteMapping("/reset-database")
    public ResponseEntity<String> resetDatabase() {
        try {
            adminService.resetDatabase();
            return ResponseEntity.ok(
                    "Baza de date a fost golită cu succes!\n" +
                            "Repornește aplicația pentru a recrea datele de test."
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare: " + e.getMessage());
        }
    }

    @GetMapping("/database-info")
    public ResponseEntity<String> getDatabaseInfo() {
        return ResponseEntity.ok(adminService.getDatabaseInfo());
    }
}