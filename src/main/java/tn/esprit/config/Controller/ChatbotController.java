package tn.Controller;

import tn.Entities.Formation;
import tn.Service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {

    @Autowired
    private FormationService formationService;

    @PostMapping("/check")
    public String chatbotResponse(@RequestBody String message) {
        message = message.replaceAll("^\"|\"$", "").toLowerCase();

        // Réponses de base
        if (message.contains("hi") || message.contains("hello")) {
            return "Hello! How can I help you today?";
        }
        if (message.contains("how are you")) {
            return "I'm just a bot, but I'm doing great! 😊";
        }

        // Vérification des formations
        List<Formation> allFormations = formationService.getAllFormations();
        for (Formation formation : allFormations) {
            if (message.contains(formation.getTitre().toLowerCase())) {
                return String.format(
                        "Yes, we found the formation: \"%s\".\nIt starts on %s and ends on %s.",
                        formation.getTitre(),
                        formation.getDateDebut(),
                        formation.getDateFin()
                );
            }
        }

        return "I'm sorry, I didn't understand that. Could you be more specific?";
    }


}
