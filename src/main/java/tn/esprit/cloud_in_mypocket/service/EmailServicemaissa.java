package tn.esprit.cloud_in_mypocket.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Async

public class EmailServicemaissa {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmailmaissa(String toEmail, String titreFormation) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Confirmation de réservation - Formation : " + titreFormation);
            message.setText("Bonjour,\n\n" +
                    "Votre réservation pour la formation \"" + titreFormation + "\" a été enregistrée avec succès.\n\n" +
                    "Merci de votre confiance.\n\nCordialement,\nL’équipe Formation");

            mailSender.send(message);
            System.out.println("Email envoyé à " + toEmail);
        } catch (Exception e) {
            System.err.println("Erreur lors de l’envoi de l’email : " + e.getMessage());
        }
    }
    // Method to send email with certificate attachment


    public void sendCertificateEmail(String to, String apprenantNom, String formationTitre, byte[] pdfData, String fileName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("🎓 Certificat de Formation : " + formationTitre);

        // Prepare HTML body
        String htmlBody = getHtmlTemplate()
                .replace("{{APPRENANT_NOM}}", apprenantNom)
                .replace("{{FORMATION_TITRE}}", formationTitre)
                .replace("{{DATE_AUJOURD_HUI}}", LocalDate.now().toString());

        helper.setText(htmlBody, true); // `true` enables HTML
        helper.addAttachment(fileName, new ByteArrayDataSource(pdfData, "application/pdf"));

        mailSender.send(message);
    }

    private String getHtmlTemplate() {
        return """
        <!DOCTYPE html>
        <html>
        <head>
          <meta charset="UTF-8">
          <style>
            body {
              font-family: 'Arial', sans-serif;
              background-color: #f4f4f4;
              padding: 30px;
            }
            .container {
              background-color: #ffffff;
              padding: 40px;
              border-radius: 10px;
              max-width: 600px;
              margin: auto;
              box-shadow: 0px 4px 8px rgba(0,0,0,0.1);
              text-align: center;
            }
            .header {
              color: #4CAF50;
              font-size: 28px;
              margin-bottom: 20px;
            }
            .sub-header {
              color: #555;
              font-size: 18px;
              margin-bottom: 40px;
            }
            .name {
              font-size: 22px;
              font-weight: bold;
              color: #333;
            }
            .formation {
              font-size: 20px;
              color: #4CAF50;
              margin-top: 10px;
              margin-bottom: 30px;
            }
            .footer {
              color: #777;
              font-size: 14px;
              margin-top: 30px;
            }
          </style>
        </head>
        <body>
          <div class="container">
            <div class="header">🎉 Félicitations !</div>
            <div class="sub-header">Ce certificat est délivré à :</div>
            <div class="name">{{APPRENANT_NOM}}</div>
            <div class="sub-header">Pour avoir complété avec succès la formation :</div>
            <div class="formation">« {{FORMATION_TITRE}} »</div>
            <div class="footer">
              Merci pour votre engagement dans l'apprentissage continu.<br/>
              📅 Date d’émission : {{DATE_AUJOURD_HUI}}
            </div>
          </div>
        </body>
        </html>
        """;
    }




}
