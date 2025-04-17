package app.Controller;

import app.Entities.Apprenant;
import app.Entities.Formation;
import app.Entities.Reservation;
import app.Repositories.ApprenantRepository;
import app.Repositories.FormationRepository;
import app.Repositories.ReservationRepository;
import app.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private ApprenantRepository apprenantRepository;

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Map<String, Object> data) {
        String nom = (String) data.get("nom");
        String email = (String) data.get("email");
        Long formationId = Long.parseLong(data.get("formationId").toString());

        Optional<Formation> formation = formationRepository.findById(formationId);
        if (formation.isEmpty()) return ResponseEntity.notFound().build();

        Apprenant apprenant = new Apprenant();
        apprenant.setNom(nom);
        apprenant.setEmail(email);
        apprenantRepository.save(apprenant);

        Reservation reservation = new Reservation();
        reservation.setApprenant(apprenant);
        reservation.setFormation(formation.get());
        reservationRepository.save(reservation);

        return ResponseEntity.ok(reservation);
    }


    @GetMapping("/formation/{id}/count")
    public ResponseEntity<Map<Long, Long>> getReservationCountsPerFormation() {
        List<Formation> formations = formationRepository.findAll();
        Map<Long, Long> counts = new HashMap<>();

        for (Formation f : formations) {
            long count = reservationRepository.countByFormationId(f.getId());
            counts.put(f.getId(), count);
        }

        return ResponseEntity.ok(counts);
    }
    @GetMapping("/stats")
    public Map<Long, Long> getReservationStats() {
        List<Object[]> stats = reservationRepository.countReservationsByFormation();
        Map<Long, Long> result = new HashMap<>();
        for (Object[] stat : stats) {
            Long formationId = (Long) stat[0];
            Long count = (Long) stat[1];
            result.put(formationId, count);
        }
        return result;
    }







    @PutMapping("/{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.updateReservation(id, reservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
