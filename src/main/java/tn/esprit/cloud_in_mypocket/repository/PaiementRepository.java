package tn.esprit.cloud_in_mypocket.repository;

import tn.esprit.cloud_in_mypocket.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
