package tn.esprit.cloud_in_mypocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.cloud_in_mypocket.entity.Contract;
import tn.esprit.cloud_in_mypocket.repository.ContractRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ContractSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(ContractSchedulerService.class);

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedRate = 60000000) // Run every minute for testing
    @Transactional
    public void updateExpiredContracts() {
        logger.info("Starting scheduled task to update expired contracts at {}", LocalDateTime.now());

        List<Contract> expiredContracts = contractRepository.findPendingContractsWithExpiredDeadline(LocalDateTime.now());
        logger.info("Found {} expired PENDING contracts", expiredContracts.size());

        for (Contract contract : expiredContracts) {
            logger.info("Updating contract ID {} from PENDING to REFUSED", contract.getId());
            contract.setStatus("REFUSED");
            contractRepository.save(contract);
        }

        logger.info("Completed scheduled task for expired contracts");
    }

    @Scheduled(fixedRate = 60000000) // Run every 30 seconds for testing
    @Transactional
    public void sendContractNotifications() {
        logger.info("Starting scheduled task to send contract notifications at {}", LocalDateTime.now());

        List<String> statuses = Arrays.asList("PENDING", "ACCEPTED", "REFUSED");
        List<Contract> contracts = contractRepository.findByStatusIn(statuses);
        logger.info("Found {} contracts with status PENDING, ACCEPTED, or REFUSED", contracts.size());

        LocalDateTime now = LocalDateTime.now();
        for (Contract contract : contracts) {
            // Notify if not notified in the last minute (for testing)
            if (contract.getLastNotified() == null || contract.getLastNotified().isBefore(now.minusMinutes(1))) {
                if (contract.getClient() != null) {
                    logger.info("Sending notification for contract ID {} with status {}", contract.getId(), contract.getStatus());
                    notificationService.sendContractNotification(contract.getClient(), contract);
                    contract.setLastNotified(now);
                    contractRepository.save(contract);
                } else {
                    logger.warn("Contract ID {} has no associated client", contract.getId());
                }
            } else {
                logger.info("Skipping notification for contract ID {} (last notified: {})",
                        contract.getId(), contract.getLastNotified());
            }
        }

        logger.info("Completed scheduled task for contract notifications");
    }
}