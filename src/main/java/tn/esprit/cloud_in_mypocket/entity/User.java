package tn.esprit.cloud_in_mypocket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private Long lawFirmId;
    private String adresseLivraison;
    //deux colonnes last login w isactive

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

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
        if (role == null) {
            role = Role.CLIENT;
        }
        //ken l user yaadi pack
        if (lastLoginDate == null) {
            lastLoginDate = LocalDateTime.now();
        }
    }
}