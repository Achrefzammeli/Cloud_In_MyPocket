package app.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateSeance;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
    public LocalDate getDateSeance() {
        return dateSeance;
    }

    // Setter
    public void setDateSeance(LocalDate dateSeance) {
        this.dateSeance = dateSeance;
    }
}
