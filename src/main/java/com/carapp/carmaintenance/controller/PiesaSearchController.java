package com.carapp.carmaintenance.controller;

import com.carapp.carmaintenance.dto.PiesaSearchResultDTO;
import com.carapp.carmaintenance.service.PiesaSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/piese-search")
public class PiesaSearchController {

    private final PiesaSearchService piesaSearchService;

    public PiesaSearchController(PiesaSearchService piesaSearchService) {
        this.piesaSearchService = piesaSearchService;
    }

    @GetMapping
    public List<PiesaSearchResultDTO> search(
            @RequestParam String q,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String codMotor
    ) {
        return piesaSearchService.search(q, marca, model, codMotor);
    }
}