package tn.esprit.cloud_in_mypocket.Controller;


import tn.esprit.cloud_in_mypocket.dto.ResponseDTO;
import tn.esprit.cloud_in_mypocket.entity.PackAbonnement;
import tn.esprit.cloud_in_mypocket.service.PackAbonnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/packs")
public class PackAbonnementController {

    @Autowired
    private PackAbonnementService packAbonnementService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createPack(@RequestBody PackAbonnement packAbonnement) {
        packAbonnementService.savePack(packAbonnement);
        ResponseDTO response = new ResponseDTO("success", "Pack created successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats-mensuelles")
    public ResponseEntity<Map<String, Map<String, Long>>> getStatsMensuelles() {
        return ResponseEntity.ok(packAbonnementService.getStatsMensuelles());
    }

    @GetMapping
    public List<PackAbonnement> getAllPacks() {
        return packAbonnementService.getAllPacks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackAbonnement> getPackById(@PathVariable Long id) {
        return packAbonnementService.getPackById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deletePack(@PathVariable Long id) {
        packAbonnementService.deletePack(id);
        ResponseDTO response = new ResponseDTO("success", "Pack deleted successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/stats")
    public ResponseEntity<ResponseDTO> getStatistiques() {
        Map<String, Long> stats = packAbonnementService.getNombreUtilisateursParType();
        ResponseDTO response = new ResponseDTO("success", "Statistiques récupérées avec succès", stats);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updatePack(@PathVariable Long id, @RequestBody PackAbonnement updatedPack) {
        packAbonnementService.updatePack(id, updatedPack);
        ResponseDTO response = new ResponseDTO("success", "Pack updated successfully");
        return ResponseEntity.ok(response);
    }


}