package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.model.*;
import com.carapp.carmaintenance.repository.MasinaRepository;
import com.carapp.carmaintenance.repository.NotificareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificareService {

    @Autowired
    private NotificareRepository notificareRepository;

    @Autowired
    private MasinaRepository masinaRepository;

    @Autowired
    private EmailService emailService;

    private static final int[] ZILE_NOTIFICARE = {60, 30, 15, 7, 1};

    public void verificaDocumente() {
        List<Masina> toateMasinile = masinaRepository.findAll();

        for (Masina masina : toateMasinile) {
            User user = masina.getUser();

            if (masina.getItp() != null) {
                verificaDocument(user, masina, masina.getItp().getDataExpirare(),
                        Notificare.TipNotificare.ITP, "ITP");
            }

            if (masina.getRovinieta() != null) {
                verificaDocument(user, masina, masina.getRovinieta().getDataExpirare(),
                        Notificare.TipNotificare.ROVINIETA, "Rovinieta");
            }

            if (masina.getAsigurare() != null) {
                verificaDocument(user, masina, masina.getAsigurare().getDataIncheiere(),
                        Notificare.TipNotificare.ASIGURARE, "Asigurare");
            }
        }
    }

    private void verificaDocument(User user, Masina masina, LocalDate dataExpirare,
                                  Notificare.TipNotificare tip, String numeDocument) {
        if (dataExpirare == null) return;

        long zileRamase = ChronoUnit.DAYS.between(LocalDate.now(), dataExpirare);

        for (int zile : ZILE_NOTIFICARE) {
            if (zileRamase == zile) {
                String titlu = numeDocument + " expiră în " + zile + " zile";
                String mesaj = "Mașina " + masina.getMarca() + " " + masina.getModel() +
                        " (" + masina.getNumarInmatriculare() + ") are " +
                        numeDocument + " care expiră pe " + dataExpirare +
                        ". Mai sunt " + zile + " zile.";

                // Salvează notificarea în DB
                Notificare notificare = new Notificare(user, titlu, mesaj, tip);
                notificareRepository.save(notificare);

                // Trimite email
                emailService.trimiteEmail(user.getEmail(), titlu, mesaj);

                break;
            }
        }
    }

    public List<Notificare> getNotificariByUserId(Long userId) {
        return notificareRepository.findByUserIdOrderByDataCreareDesc(userId);
    }

    public List<Notificare> getNotificariNecititeByUserId(Long userId) {
        return notificareRepository.findByUserIdAndCititaFalse(userId);
    }

    public long getNumarNotificariNecitite(Long userId) {
        return notificareRepository.countByUserIdAndCititaFalse(userId);
    }

    public void marcheazaCaCitita(Long notificareId) {
        notificareRepository.findById(notificareId).ifPresent(n -> {
            n.setCitita(true);
            notificareRepository.save(n);
        });
    }

    public void marcheazaToateCaCitite(Long userId) {
        List<Notificare> necitite = notificareRepository.findByUserIdAndCititaFalse(userId);
        necitite.forEach(n -> n.setCitita(true));
        notificareRepository.saveAll(necitite);
    }
}