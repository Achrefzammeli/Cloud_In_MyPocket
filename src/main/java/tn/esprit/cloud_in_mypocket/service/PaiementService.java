package tn.esprit.cloud_in_mypocket.service;

import tn.esprit.cloud_in_mypocket.entity.Paiement;
import tn.esprit.cloud_in_mypocket.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaiementService {
    @Autowired
    private PaiementRepository paiementRepository;

    public Paiement ajouterPaiement(Paiement paiement) {
        paiement.setDatePaiement(LocalDate.now());
        return paiementRepository.save(paiement);
    }

    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }
}
