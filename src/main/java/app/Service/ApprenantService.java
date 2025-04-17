package app.Service;


import app.Entities.Apprenant;
import app.Repositories.ApprenantRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Getter
@Setter
@Service
public class ApprenantService {

    @Autowired
    private ApprenantRepository apprenantRepository;

    public List<Apprenant> getAllApprenants() {
        return apprenantRepository.findAll();
    }

    public Apprenant getApprenantById(Long id) {
        return apprenantRepository.findById(id).orElse(null);
    }

    public Apprenant createApprenant(Apprenant apprenant) {
        return apprenantRepository.save(apprenant);
    }

    public Apprenant updateApprenant(Long id, Apprenant update) {
        Apprenant existing = apprenantRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setNom(update.getNom());
            existing.setNom(update.getNom());
            return apprenantRepository.save(existing);
        }
        return null;
    }

    public void deleteApprenant(Long id) {
        apprenantRepository.deleteById(id);
    }

}


