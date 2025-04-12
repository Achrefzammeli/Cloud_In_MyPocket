package tn.esprit.cloud_in_mypocket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String numeroDeTelephone;
    private String role;
    private Long lawFirmId;
    private String adresseLivraison;

    // Relation avec PackAbonnement (un utilisateur peut souscrire à un pack)
    @ManyToOne
    @JoinColumn(name = "pack_abonnement_id")
    private PackAbonnement packAbonnement;

    // Relation avec Feedback (un utilisateur peut laisser plusieurs feedbacks)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubscriptionHistory> subscriptionHistory; // Historique des souscriptions
    @PrePersist
    public void prePersist() {
        if (role == null || role.trim().isEmpty()) {
            role = "CLIENT";
        }
    }
}