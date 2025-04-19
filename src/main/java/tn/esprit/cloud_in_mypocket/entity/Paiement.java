package tn.esprit.cloud_in_mypocket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;
    private String methode; // ex : "Carte", "PayPal", etc.
    private LocalDate datePaiement;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private PackAbonnement packAbonnement;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User utilisateur;

    // Getters & Setters
}
