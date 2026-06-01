package com.carapp.carmaintenance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificareScheduler {

    @Autowired
    private NotificareService notificareService;


    @Scheduled(cron = "0 0 16 * * *")
    public void verificaDocumenteZilnic() {
        System.out.println("Verificare documente...");
        notificareService.verificaDocumente();
        System.out.println("Verificare finalizată!");
    }
}