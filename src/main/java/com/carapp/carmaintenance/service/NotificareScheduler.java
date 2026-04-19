package com.carapp.carmaintenance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificareScheduler {

    @Autowired
    private NotificareService notificareService;

    // Rulează în fiecare zi la miezul nopții
    @Scheduled(cron = "0 0 0 * * *")
    public void verificaDocumenteZilnic() {
        System.out.println("Verificare documente...");
        notificareService.verificaDocumente();
        System.out.println("Verificare finalizată!");
    }
}