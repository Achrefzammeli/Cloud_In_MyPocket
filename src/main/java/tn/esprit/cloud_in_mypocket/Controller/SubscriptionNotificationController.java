package tn.esprit.cloud_in_mypocket.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.cloud_in_mypocket.service.SubscriptionNotificationService;

@RestController
@RequestMapping("/api/subscription-notifications")
@RequiredArgsConstructor
public class SubscriptionNotificationController {

    private final SubscriptionNotificationService subscriptionNotificationService;

    @PostMapping("/check-expiring")
    public ResponseEntity<String> checkExpiringSubscriptions() {
        try {
            subscriptionNotificationService.checkExpiringSubscriptions();
            return ResponseEntity.ok("Vérification des abonnements expirants effectuée avec succès");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur lors de la vérification des abonnements: " + e.getMessage());
        }
    }
} 