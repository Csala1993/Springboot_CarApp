package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    @Autowired private UserRepository userRepository;
    @Autowired private MasinaRepository masinaRepository;
    @Autowired private AsigurareRepository asigurareRepository;
    @Autowired private PiesaRepository piesaRepository;
    @Autowired private IstoricServiceRepository istoricServiceRepository;

    @Transactional
    public void resetDatabase() {
        istoricServiceRepository.deleteAll();
        piesaRepository.deleteAll();
        masinaRepository.deleteAll();
        asigurareRepository.deleteAll();
        userRepository.deleteAll();
    }

    public String getDatabaseInfo() {
        return "===========================================\n" +
                "Informații Bază de Date:\n" +
                "- " + userRepository.count() + " utilizatori\n" +
                "- " + masinaRepository.count() + " mașini\n" +
                "- " + asigurareRepository.count() + " asigurări\n" +
                "- " + piesaRepository.count() + " piese\n" +
                "- " + istoricServiceRepository.count() + " servicii efectuate\n" +
                "===========================================";
    }
}