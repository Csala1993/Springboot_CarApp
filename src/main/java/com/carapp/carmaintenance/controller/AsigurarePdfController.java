package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.dto.AsigurareExtractDTO;
import com.carapp.carmaintenance.service.AsigurarePdfExtractService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/asigurari")
public class AsigurarePdfController {

    private final AsigurarePdfExtractService asigurarePdfExtractService;

    public AsigurarePdfController(AsigurarePdfExtractService asigurarePdfExtractService) {
        this.asigurarePdfExtractService = asigurarePdfExtractService;
    }

    @PostMapping(value = "/extract-pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> extractPdf(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("PDF primit: " + file.getOriginalFilename());
            System.out.println("Marime PDF: " + file.getSize());

            AsigurareExtractDTO result = asigurarePdfExtractService.extrageDate(file);

            System.out.println("Date extrase:");
            System.out.println("Asigurator: " + result.getNumeAsigurator());
            System.out.println("Proprietar: " + result.getNumeProprietar());
            System.out.println("VIN: " + result.getVinMasina());
            System.out.println("Data inceput: " + result.getDataInceput());
            System.out.println("Data incheiere: " + result.getDataIncheiere());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Eroare extragere PDF: " + e.getMessage());
        }
    }
}