package tn.esprit.cloud_in_mypocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendInactivityReminder(String to, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Rappel d'inactivité - Cloud In My Pocket");
        message.setText(
            "Bonjour " + username + ",\n\n" +
            "Nous avons remarqué que vous n'avez pas utilisé votre compte depuis un certain temps.\n" +
            "Pour éviter la désactivation automatique de votre compte, nous vous invitons à vous connecter.\n\n" +
            "Cordialement,\n" +
            "L'équipe Cloud In My Pocket"
        );

        mailSender.send(message);
    }
} 