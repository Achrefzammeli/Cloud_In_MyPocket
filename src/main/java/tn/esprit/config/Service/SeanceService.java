package tn.Service;

import tn.Entities.Seance;
import tn.Repositories.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    @Lazy  // Lazy initialization of SeanceService to avoid circular dependency

    private ApprenantService apprenantService;
@Autowired
private EmailServicemaissa emailServicemaissa;
    @Autowired
    private PdfGeneratorService pdfGeneratorService;
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

    public SeanceService(ApprenantService apprenantService) {
        this.apprenantService = apprenantService;
    }
    public void handleSessionCompletion(String apprenantNom, String apprenantEmail, String formationTitre) throws Exception {
        byte[] pdf = pdfGeneratorService.generateCertificate(apprenantNom, formationTitre);
        emailServicemaissa.sendCertificateEmail(
                apprenantEmail,
                apprenantNom,
                formationTitre,
                pdf,
                "Certificat-" + formationTitre + ".pdf"
        );
    }


}
