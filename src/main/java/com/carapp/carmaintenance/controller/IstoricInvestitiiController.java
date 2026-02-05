package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.dto.InvestitieRequestDTO;
import com.carapp.carmaintenance.dto.IstoricInvestitiiSimpleDTO;
import com.carapp.carmaintenance.model.IstoricInvestitii;
import com.carapp.carmaintenance.service.IstoricInvestitiiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investitii")
@CrossOrigin(origins = "*")
public class IstoricInvestitiiController {

    @Autowired
    private IstoricInvestitiiService investitiiService;


    @PostMapping("/masina/{masinaId}")
    public ResponseEntity<?> add(@PathVariable Long masinaId, @RequestBody InvestitieRequestDTO dto) {
        try {
            IstoricInvestitii saved = investitiiService.adaugaInvestitie(masinaId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/masina/{masinaId}")
    public ResponseEntity<List<IstoricInvestitiiSimpleDTO>> getByMasina(@PathVariable Long masinaId) {
        return ResponseEntity.ok(investitiiService.getInvestitiiByMasina(masinaId));
    }


    @DeleteMapping("/{investitieId}")
    public ResponseEntity<?> delete(@PathVariable Long investitieId) {
        investitiiService.stergeInvestitie(investitieId);
        return ResponseEntity.ok("Investiție ștearsă cu succes!");
    }
}
