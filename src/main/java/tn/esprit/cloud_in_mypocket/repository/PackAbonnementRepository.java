package tn.esprit.cloud_in_mypocket.repository;

import com.example.gestionuser.entity.PackAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackAbonnementRepository extends JpaRepository<PackAbonnement, Long> {
}