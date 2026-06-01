package com.carapp.carmaintenance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailExpeditor;

    public void trimiteEmail(String catre, String subiect, String continut) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(catre);
            message.setSubject(subiect);
            message.setText(continut);
            message.setFrom(emailExpeditor);

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Eroare la trimiterea emailului: " + e.getMessage());
        }
    }

    public void trimiteEmailBunVenit(String catre, String username) {
        String subiect = "Bun venit in Auto Track!";

        String continut = "Salut, " + username + "!\n\n"
                + "Contul tau a fost creat cu succes.\n\n"
                + "De acum poti adauga masinile tale, poti urmari ITP-ul, rovinieta, asigurarea, "
                + "istoricul de service si investitiile facute.\n\n"
                + "Drumuri bune!\n"
                + "Echipa Auto Care";

        trimiteEmail(catre, subiect, continut);
    }
}