package tn.esprit.cloud_in_mypocket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "packs_abonnement")
public class PackAbonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom; // Exemple : "Pack Basique", "Pack Premium"
    private String description; // Description du pack
    private double prix; // Prix du pack
    private int duree; // Durée en jours
    @Enumerated(EnumType.STRING) // Stocker le type de pack sous forme de chaîne
    private PackType type; // Silver, Gold, Platinum
    // Relation avec l'entité User (un pack peut être souscrit par plusieurs utilisateurs)
    @OneToMany(mappedBy = "packAbonnement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    // Relation avec l'entité Feedback (un pack peut avoir plusieurs feedbacks)
    @OneToMany(mappedBy = "packAbonnement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;
}