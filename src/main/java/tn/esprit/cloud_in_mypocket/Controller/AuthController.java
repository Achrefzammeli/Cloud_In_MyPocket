package tn.esprit.cloud_in_mypocket.Controller;

import tn.esprit.cloud_in_mypocket.entity.User;
import tn.esprit.cloud_in_mypocket.payload.LoginRequest;
import tn.esprit.cloud_in_mypocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
// Si vous avez une configuration CORS globale, vous pouvez retirer cette annotation
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Utilisateur non trouvé"));
        }

        User user = optionalUser.get();
        if (!passwordEncoder.matches(loginRequest.getMotDePasse(), user.getMotDePasse())) {
            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Mot de passe incorrect"));
        }

        // Hide the password before sending the user
        user.setMotDePasse(null);

        return ResponseEntity.ok(user);
    }
}