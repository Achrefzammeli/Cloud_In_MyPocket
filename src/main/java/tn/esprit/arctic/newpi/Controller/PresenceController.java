package tn.esprit.arctic.newpi.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.arctic.newpi.dto.PresenceDto;
import tn.esprit.arctic.newpi.dto.PresenceRequest;
import tn.esprit.arctic.newpi.entity.Presence;
import tn.esprit.arctic.newpi.service.PresenceService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/presences")
public class PresenceController {

    @Autowired
    private PresenceService presenceService;

    @GetMapping("/jour/{date}")
    public ResponseEntity<List<PresenceDto>> getPresencesByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(presenceService.getPresencesByDate(date));
    }

    @PostMapping("/marquer")
    public ResponseEntity<Presence> enregistrerPresence(@RequestBody PresenceRequest request) {
        return ResponseEntity.ok(
                presenceService.enregistrerPresence(request.getEmployeeId(), request.isPresent(), request.getHeureArrivee())
        );
    }
}


