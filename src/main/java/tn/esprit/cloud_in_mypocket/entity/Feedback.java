package tn.esprit.cloud_in_mypocket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentaire; // Commentaire de l'utilisateur
    private int note; // Note sur 5 ou 10, par exemple

    // Relation avec l'entité User (un feedback est lié à un utilisateur)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relation avec l'entité PackAbonnement (un feedback est lié à un pack)
    @ManyToOne
    @JoinColumn(name = "pack_abonnement_id", nullable = false)
    private PackAbonnement packAbonnement;
}