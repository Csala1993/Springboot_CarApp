package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.model.Notificare;
import com.carapp.carmaintenance.model.User;
import com.carapp.carmaintenance.repository.MasinaRepository;
import com.carapp.carmaintenance.repository.NotificareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private CurrentUserService currentUserService;

    private static final int[] ZILE_NOTIFICARE = {60, 30, 15, 7, 1};

    @Transactional
    public void verificaDocumente() {
        List<Masina> toateMasinile = masinaRepository.findAll();

        for (Masina masina : toateMasinile) {
            User user = masina.getUser();

            if (masina.getItp() != null) {
                verificaDocument(
                        user,
                        masina,
                        masina.getItp().getDataExpirare(),
                        Notificare.TipNotificare.ITP,
                        "ITP"
                );
            }

            if (masina.getRovinieta() != null) {
                verificaDocument(
                        user,
                        masina,
                        masina.getRovinieta().getDataExpirare(),
                        Notificare.TipNotificare.ROVINIETA,
                        "Rovinieta"
                );
            }

            if (masina.getAsigurare() != null) {
                verificaDocument(
                        user,
                        masina,
                        masina.getAsigurare().getDataIncheiere(),
                        Notificare.TipNotificare.ASIGURARE,
                        "Asigurare"
                );
            }
        }
    }

    private void verificaDocument(
            User user,
            Masina masina,
            LocalDate dataExpirare,
            Notificare.TipNotificare tip,
            String numeDocument
    ) {
        if (dataExpirare == null) {
            return;
        }

        long zileRamase = ChronoUnit.DAYS.between(
                LocalDate.now(),
                dataExpirare
        );

        for (int zile : ZILE_NOTIFICARE) {
            if (zileRamase == zile) {
                String titlu =
                        numeDocument + " expira in " + zile + " zile";

                String mesaj =
                        "Masina " +
                                masina.getMarca() + " " +
                                masina.getModel() + " (" +
                                masina.getNumarInmatriculare() + ") are " +
                                numeDocument + " care expira pe " +
                                dataExpirare + ". Mai sunt " +
                                zile + " zile.";

                Notificare notificare = new Notificare(
                        user,
                        titlu,
                        mesaj,
                        tip
                );

                notificareRepository.save(notificare);

                emailService.trimiteEmail(
                        user.getEmail(),
                        titlu,
                        mesaj
                );

                break;
            }
        }
    }

    public List<Notificare> getNotificarileMele() {
        Long userId = currentUserService.getCurrentUserId();

        return notificareRepository
                .findByUserIdOrderByDataCreareDesc(userId);
    }

    public List<Notificare> getNotificarileMeleNecitite() {
        Long userId = currentUserService.getCurrentUserId();

        return notificareRepository
                .findByUserIdAndCititaFalse(userId);
    }

    public long getNumarulMeuDeNotificariNecitite() {
        Long userId = currentUserService.getCurrentUserId();

        return notificareRepository
                .countByUserIdAndCititaFalse(userId);
    }

    @Transactional
    public void marcheazaCaCitita(Long notificareId) {
        Long userId = currentUserService.getCurrentUserId();

        Notificare notificare = notificareRepository
                .findByIdAndUserId(notificareId, userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Notificarea nu exista sau nu iti apartine."
                        )
                );

        notificare.setCitita(true);
        notificareRepository.save(notificare);
    }

    @Transactional
    public void marcheazaToateAleMeleCaCitite() {
        Long userId = currentUserService.getCurrentUserId();

        List<Notificare> necitite =
                notificareRepository.findByUserIdAndCititaFalse(userId);

        necitite.forEach(notificare ->
                notificare.setCitita(true)
        );

        notificareRepository.saveAll(necitite);
    }
}