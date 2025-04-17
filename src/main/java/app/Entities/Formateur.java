package app.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Formateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotBlank

    private String name;
    @NotBlank

    private String lastname;
    @NotBlank


    private String email;

    @OneToMany(mappedBy = "formateur")
    private List<Formation> formations;
    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getemail() {
        return email;
    }
    public void setemail(String email) {
        this.email = email;
    }
    public void setid(Long id) {
        this.id = id;
    }
    public Long getid() {
        return id;
    }



}
