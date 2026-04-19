package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.model.Notificare;
import com.carapp.carmaintenance.service.NotificareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificari")
@CrossOrigin(origins = "*")
public class NotificareController {

    @Autowired
    private NotificareService notificareService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notificare>> getNotificari(@PathVariable Long userId) {
        return ResponseEntity.ok(notificareService.getNotificariByUserId(userId));
    }

    @GetMapping("/user/{userId}/necitite")
    public ResponseEntity<List<Notificare>> getNotificariNecitite(@PathVariable Long userId) {
        return ResponseEntity.ok(notificareService.getNotificariNecititeByUserId(userId));
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getNumarNecitite(@PathVariable Long userId) {
        return ResponseEntity.ok(notificareService.getNumarNotificariNecitite(userId));
    }

    @PutMapping("/{id}/citita")
    public ResponseEntity<?> marcheazaCitita(@PathVariable Long id) {
        notificareService.marcheazaCaCitita(id);
        return ResponseEntity.ok("Notificare marcată ca citită!");
    }

    @PutMapping("/user/{userId}/toate-citite")
    public ResponseEntity<?> marcheazaToateCitite(@PathVariable Long userId) {
        notificareService.marcheazaToateCaCitite(userId);
        return ResponseEntity.ok("Toate notificările marcate ca citite!");
    }


}