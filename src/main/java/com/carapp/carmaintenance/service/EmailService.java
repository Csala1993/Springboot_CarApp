package com.carapp.carmaintenance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void trimiteEmail(String catre, String subiect, String continut) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(catre);
            message.setSubject(subiect);
            message.setText(continut);
            message.setFrom("sebastiancsala2004@gmail.com"); // emailul tau Gmail
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Eroare la trimiterea emailului: " + e.getMessage());
        }
    }
}