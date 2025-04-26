package tn.esprit.cloud_in_mypocket.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.cloud_in_mypocket.entity.PackAbonnement;
import tn.esprit.cloud_in_mypocket.entity.SubscriptionHistory;
import tn.esprit.cloud_in_mypocket.entity.User;
import tn.esprit.cloud_in_mypocket.repository.SubscriptionHistoryRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionNotificationService {

    private final SubscriptionHistoryRepository subscriptionHistoryRepository;
    private final JavaMailSender mailSender;

    @Scheduled(cron = "0 0 9 * * ?") // Exécuté tous les jours à 9h
    public void checkExpiringSubscriptions() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate dayAfterTomorrow = LocalDate.now().plusDays(2);

        // Récupérer les abonnements qui expirent demain ou après-demain
        List<SubscriptionHistory> expiringSubscriptions = subscriptionHistoryRepository
                .findByEndDateBetween(tomorrow, dayAfterTomorrow);

        for (SubscriptionHistory subscription : expiringSubscriptions) {
            sendRenewalNotification(subscription);
        }
    }

    private void sendRenewalNotification(SubscriptionHistory subscription) {
        try {
            User user = subscription.getUser();
            PackAbonnement pack = subscription.getPackAbonnement();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Renouvellement de votre abonnement - Cloud In My Pocket");

            String emailContent = String.format(
                    "<html>" +
                            "<body style='font-family: Arial, sans-serif;'>" +
                            "<h2 style='color: #2c3e50;'>Bonjour %s %s,</h2>" +
                            "<p>Votre abonnement au pack <strong>%s</strong> expire le <strong>%s</strong>.</p>" +
                            "<p>Pour continuer à bénéficier de nos services, nous vous invitons à renouveler votre abonnement dès que possible.</p>" +
                            "<p>Détails de votre abonnement actuel :</p>" +
                            "<ul>" +
                            "<li>Pack : %s</li>" +
                            "<li>Type : %s</li>" +
                            "<li>Prix mensuel : %.2f TND</li>" +
                            "<li>Prix annuel : %.2f TND</li>" +
                            "</ul>" +
                            "<p>Pour renouveler votre abonnement, veuillez vous connecter à votre compte.</p>" +
                            "<p>Cordialement,<br>L'équipe Cloud In My Pocket</p>" +
                            "</body>" +
                            "</html>",
                    user.getNom(),
                    user.getPrenom(),
                    pack.getNom(),
                    subscription.getEndDate(),
                    pack.getNom(),
                    pack.getType(),
                    pack.getPrixMensuel(),
                    pack.getPrixAnnuel()
            );

            helper.setText(emailContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            // Log l'erreur
            e.printStackTrace();
        }
    }
} 