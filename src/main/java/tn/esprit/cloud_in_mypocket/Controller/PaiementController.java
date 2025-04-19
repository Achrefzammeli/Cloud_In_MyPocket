package tn.esprit.cloud_in_mypocket.Controller;

import tn.esprit.cloud_in_mypocket.dto.ResponseDTO;
import tn.esprit.cloud_in_mypocket.entity.Paiement;
import tn.esprit.cloud_in_mypocket.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packs")
public class PaiementController {
    @Autowired
    private PaiementService paiementService;

    @PostMapping("/paiements")
    public ResponseEntity<ResponseDTO> effectuerPaiement(@RequestBody Paiement paiement) {
        Paiement savedPaiement = paiementService.ajouterPaiement(paiement);
        ResponseDTO response = new ResponseDTO("success", "Paiement effectué avec succès", savedPaiement);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/historique")
    public ResponseEntity<ResponseDTO> afficherTousLesPaiements() {
        List<Paiement> paiements = paiementService.getAllPaiements();
        ResponseDTO response = new ResponseDTO("success", "Liste des paiements récupérée avec succès", paiements);
        return ResponseEntity.ok(response);
    }
}