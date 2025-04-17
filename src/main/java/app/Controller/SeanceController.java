package app.Controller;

import app.Entities.Seance;
import app.Service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seances")
@CrossOrigin(origins = "*")
public class SeanceController {

    @Autowired
    private SeanceService seanceService;

    @GetMapping
    public List<Seance> getAll() {
        return seanceService.getAllSeances();
    }

    @GetMapping("/{id}")
    public Seance getById(@PathVariable Long id) {
        return seanceService.getSeanceById(id);
    }

    @PostMapping
    public Seance create(@RequestBody Seance seance) {
        return seanceService.createSeance(seance);
    }

    @PutMapping("/{id}")
    public Seance update(@PathVariable Long id, @RequestBody Seance seance) {
        return seanceService.updateSeance(id, seance);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        seanceService.deleteSeance(id);
    }
}
