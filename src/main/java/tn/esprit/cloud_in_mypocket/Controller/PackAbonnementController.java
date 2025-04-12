package tn.esprit.cloud_in_mypocket.Controller;


import tn.esprit.cloud_in_mypocket.dto.ResponseDTO;
import tn.esprit.cloud_in_mypocket.entity.PackAbonnement;
import tn.esprit.cloud_in_mypocket.service.PackAbonnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packs")
public class PackAbonnementController {

    @Autowired
    private PackAbonnementService packAbonnementService;

    @PostMapping
    public ResponseEntity<PackAbonnement> createPack(@RequestBody PackAbonnement packAbonnement) {
        PackAbonnement savedPack = packAbonnementService.savePack(packAbonnement);
        return ResponseEntity.ok(savedPack);
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
}