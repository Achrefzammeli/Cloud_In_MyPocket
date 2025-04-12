package tn.esprit.cloud_in_mypocket.service;

import com.example.gestionuser.entity.PackAbonnement;
import com.example.gestionuser.repository.PackAbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackAbonnementService {

    @Autowired
    private PackAbonnementRepository packAbonnementRepository;

    public PackAbonnement savePack(PackAbonnement packAbonnement) {
        return packAbonnementRepository.save(packAbonnement);
    }

    public List<PackAbonnement> getAllPacks() {
        return packAbonnementRepository.findAll();
    }

    public Optional<PackAbonnement> getPackById(Long id) {
        return packAbonnementRepository.findById(id);
    }

    public void deletePack(Long id) {
        packAbonnementRepository.deleteById(id);
    }
}