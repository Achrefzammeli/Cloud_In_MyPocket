package tn.Controller;

import tn.Entities.Apprenant;
import tn.Repositories.ApprenantRepository;
import tn.Repositories.SeanceRepository;
import tn.Service.ApprenantService;
import tn.Service.EmailServicemaissa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apprenants")
@CrossOrigin(origins = "*")
public class ApprenantController {

    @Autowired
    private ApprenantService apprenantService;
    private ApprenantRepository apprenantRepository;
    @Autowired
    private EmailServicemaissa emailServicemaissa;
    @Autowired
    private SeanceRepository seanceRepository;

    @GetMapping
    public List<Apprenant> getAll() {
        return apprenantService.getAllApprenants();
    }

    @GetMapping("/{id}")
    public Apprenant getById(@PathVariable Long id) {
        return apprenantService.getApprenantById(id);
    }

    @PostMapping
    public Apprenant create(@RequestBody Apprenant apprenant) {
        return apprenantService.createApprenant(apprenant);
    }

    @PutMapping("/{id}")
    public Apprenant update(@PathVariable Long id, @RequestBody Apprenant apprenant) {
        return apprenantService.updateApprenant(id, apprenant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        apprenantService.deleteApprenant(id);
    }

}


