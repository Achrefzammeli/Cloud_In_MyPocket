package tn.esprit.arctic.newpi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.arctic.newpi.entity.Employee;
import tn.esprit.arctic.newpi.entity.Presence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PresenceRepository extends JpaRepository<Presence, Long> {
    List<Presence> findByDate(LocalDate date);
    boolean existsByEmployeeAndDate(Employee employee, LocalDate date);
    Optional<Presence> findByEmployeeAndDate(Employee employee, LocalDate date);

}

