package tn.esprit.cloud_in_mypocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.cloud_in_mypocket.entity.Paiement;
import tn.esprit.cloud_in_mypocket.entity.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    //List<Paiement> findByDatePaiementBetween(LocalDate startDate, LocalDate endDate);
    List<Paiement> findByUtilisateurIdAndDatePaiementBetween(Long utilisateurId, LocalDate startDate, LocalDate endDate);
    List<Paiement> findByUtilisateurOrderByDatePaiementDesc(User user);

}
