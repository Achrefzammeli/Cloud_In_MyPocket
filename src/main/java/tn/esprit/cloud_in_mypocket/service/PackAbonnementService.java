package tn.esprit.cloud_in_mypocket.service;

import tn.esprit.cloud_in_mypocket.entity.PackAbonnement;
import tn.esprit.cloud_in_mypocket.entity.StatistiquesPackAbonnement;
import tn.esprit.cloud_in_mypocket.repository.PackAbonnementRepository;
import tn.esprit.cloud_in_mypocket.repository.StatistiquesPackAbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PackAbonnementService {

    private static final Logger logger = LoggerFactory.getLogger(PackAbonnementService.class);

    @Autowired
    private PackAbonnementRepository packAbonnementRepository;

    @Autowired
    private StatistiquesPackAbonnementRepository statistiquesPackAbonnementRepository;

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

    public Map<String, Long> getNombreUtilisateursParType() {
        List<PackAbonnement> packs = packAbonnementRepository.findAll();
        Map<String, Long> stats = new HashMap<>();

        for (PackAbonnement pack : packs) {
            String type = pack.getType().toString();
            long count = (pack.getUsers() != null) ? pack.getUsers().size() : 0;
            stats.put(type, stats.getOrDefault(type, 0L) + count);
        }

        return stats;
    }

    @Scheduled(fixedRate = 10000) // Exécute toutes les 10 secondes
    public void afficherStatistiquesUtilisateurs() {
        Map<String, Long> stats = getNombreUtilisateursParType();
        logger.info("📊 Statistiques des utilisateurs par type de pack : {}", stats);

        // Enregistrer les statistiques dans la base de données
        for (Map.Entry<String, Long> entry : stats.entrySet()) {
            StatistiquesPackAbonnement statistique = new StatistiquesPackAbonnement();
            statistique.setTypePack(entry.getKey());
            statistique.setNombreUtilisateurs(entry.getValue());
            statistique.setDate(java.time.LocalDateTime.now().toString()); // Enregistrer la date/heure actuelle
            statistiquesPackAbonnementRepository.save(statistique);
        }
    }

    @Scheduled(cron = "0 0/30 * * * *") // Exécute chaque début d'heure
    public void verifierPacksSansUtilisateurs() {
        List<PackAbonnement> packs = packAbonnementRepository.findAll();
        List<PackAbonnement> packsVides = packs.stream()
                .filter(p -> p.getUsers() == null || p.getUsers().isEmpty())
                .collect(Collectors.toList());

        if (!packsVides.isEmpty()) {
            logger.warn("⚠️ Packs sans utilisateurs détectés : {}", packsVides);
        } else {
            logger.info("✅ Tous les packs ont au moins un utilisateur.");
        }
    }

    public PackAbonnement updatePack(Long id, PackAbonnement updatedPack) {
        Optional<PackAbonnement> existingPackOpt = packAbonnementRepository.findById(id);

        if (existingPackOpt.isPresent()) {
            PackAbonnement existingPack = existingPackOpt.get();

            // Mise à jour des champs
            existingPack.setNom(updatedPack.getNom());
            existingPack.setDescription(updatedPack.getDescription());
            existingPack.setPrix(updatedPack.getPrix());
            existingPack.setPrixAnnuel(updatedPack.getPrixAnnuel());
            existingPack.setPrixMensuel(updatedPack.getPrixMensuel());
            existingPack.setDuree(updatedPack.getDuree());
            existingPack.setType(updatedPack.getType());

            // Sauvegarder les modifications
            return packAbonnementRepository.save(existingPack);
        } else {
            throw new RuntimeException("Pack avec l'ID " + id + " introuvable.");
        }
    }
}