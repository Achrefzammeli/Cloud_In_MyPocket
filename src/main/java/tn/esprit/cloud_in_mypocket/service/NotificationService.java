package tn.esprit.cloud_in_mypocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tn.esprit.cloud_in_mypocket.entity.User;
import tn.esprit.cloud_in_mypocket.entity.Contract;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendContractNotification(User user, Contract contract) {
        String message;
        switch (contract.getStatus()) {
            case "PENDING":
                message = String.format("Reminder: Dear %s, your contract (ID: %d) is still PENDING. Please review it before the deadline: %s.",
                        user.getNom(), contract.getId(), contract.getDeadline());
                break;
            case "ACCEPTED":
                message = String.format("Dear %s, your contract (ID: %d) has been ACCEPTED.", user.getNom(), contract.getId());
                break;
            case "REFUSED":
                message = String.format("Dear %s, your contract (ID: %d) has been REFUSED.", user.getNom(), contract.getId());
                break;
            default:
                message = String.format("Dear %s, your contract (ID: %d) has an unknown status: %s.",
                        user.getNom(), contract.getId(), contract.getStatus());
        }
        // Placeholder: Log the notification (replace with email or other notification system)
        logger.info("Sending notification to client ID {}: {}", user.getId(), message);
        // Example for email integration (uncomment and configure if needed):
        /*
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(client.getEmail()); // Assumes Client has an email field
            mail.setSubject("Contract Status Update");
            mail.setText(message);
            javaMailSender.send(mail);
        } catch (Exception e) {
            logger.error("Failed to send email to client ID {}: {}", client.getId(), e.getMessage());
        }
        */
    }
}