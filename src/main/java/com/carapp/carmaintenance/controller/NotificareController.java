package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.model.Notificare;
import com.carapp.carmaintenance.service.NotificareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificari")
@CrossOrigin(origins = "*")
public class NotificareController {

    private final NotificareService notificareService;

    public NotificareController(NotificareService notificareService) {
        this.notificareService = notificareService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<Notificare>> getNotificarileMele() {
        return ResponseEntity.ok(
                notificareService.getNotificarileMele()
        );
    }

    @GetMapping("/me/necitite")
    public ResponseEntity<List<Notificare>> getNotificarileMeleNecitite() {
        return ResponseEntity.ok(
                notificareService.getNotificarileMeleNecitite()
        );
    }

    @GetMapping("/me/count")
    public ResponseEntity<Long> getNumarulMeuDeNecitite() {
        return ResponseEntity.ok(
                notificareService.getNumarulMeuDeNotificariNecitite()
        );
    }

    @PutMapping("/{id}/citita")
    public ResponseEntity<?> marcheazaCitita(@PathVariable Long id) {
        notificareService.marcheazaCaCitita(id);
        return ResponseEntity.ok("Notificare marcata ca citita.");
    }

    @PutMapping("/me/toate-citite")
    public ResponseEntity<?> marcheazaToateCitite() {
        notificareService.marcheazaToateAleMeleCaCitite();
        return ResponseEntity.ok(
                "Toate notificarile au fost marcate ca citite."
        );
    }
}