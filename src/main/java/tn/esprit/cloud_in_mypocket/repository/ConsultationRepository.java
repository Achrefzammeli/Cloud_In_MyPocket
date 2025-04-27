package tn.esprit.cloud_in_mypocket.repository;

import tn.esprit.cloud_in_mypocket.entity.Consultation;
import tn.esprit.cloud_in_mypocket.entity.Dossier;
import tn.esprit.cloud_in_mypocket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long>, JpaSpecificationExecutor<Consultation> {
    List<Consultation> findByDossier(Dossier dossier);
    List<Consultation> findByStatus(String status);
    
    // Updated method names to use slotStart instead of dateHeure
    List<Consultation> findBySlotStartAfter(LocalDateTime dateTime);
    List<Consultation> findBySlotStartBefore(LocalDateTime dateTime);
    List<Consultation> findBySlotStartBeforeAndStatus(LocalDateTime dateTime, String status);

    // Updated query to use slotStart instead of dateHeure
    @Query("SELECT c FROM Consultation c WHERE c.slotStart >= ?1 AND c.slotStart < ?2")
    List<Consultation> findConsultationsForDay(LocalDateTime startOfDay, LocalDateTime endOfDay);
    
    // Add method to prevent double-booking
    boolean existsByLawyerAndSlotStartAndStatusNot(User lawyer, LocalDateTime slotStart, String cancelled);
    
    // Add method to find all consultations for a lawyer within a time period
    List<Consultation> findAllByLawyerAndSlotStartBetween(User lawyer, LocalDateTime start, LocalDateTime end);
}
