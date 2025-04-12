package tn.esprit.cloud_in_mypocket.repository;

import com.example.gestionuser.entity.SubscriptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionHistoryRepository extends JpaRepository<SubscriptionHistory, Long> {
    // Trouver l'historique des souscriptions d'un utilisateur
    List<SubscriptionHistory> findByUserId(Long userId);
}