package app.Service;

import app.Entities.Seance;
import app.Repositories.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;

    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    public Seance getSeanceById(Long id) {
        return seanceRepository.findById(id).orElse(null);
    }

    public Seance createSeance(Seance seance) {
        return seanceRepository.save(seance);
    }

    public Seance updateSeance(Long id, Seance updated) {
        Seance existing = seanceRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setDateSeance(updated.getDateSeance());
            return seanceRepository.save(existing);
        }
        return null;
    }

    public void deleteSeance(Long id) {
        seanceRepository.deleteById(id);
    }
}
