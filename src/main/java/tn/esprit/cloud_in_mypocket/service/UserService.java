package tn.esprit.cloud_in_mypocket.service;

import tn.esprit.cloud_in_mypocket.entity.Role;
import tn.esprit.cloud_in_mypocket.entity.User;
import tn.esprit.cloud_in_mypocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Un compte avec cet email existe déjà.");
        }
        if (user.getMotDePasse() != null && !user.getMotDePasse().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(user.getMotDePasse());
            user.setMotDePasse(hashedPassword);
        }
        
        // Ensure role is set
        if (user.getRole() == null) {
            user.setRole(Role.CLIENT);
        }
        
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Méthode pour supprimer un utilisateur par son id
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

}
