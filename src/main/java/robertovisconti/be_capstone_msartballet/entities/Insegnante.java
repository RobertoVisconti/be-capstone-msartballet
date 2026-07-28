package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Insegnante extends Utente {

    @Id
    @GeneratedValue
    @Column(name = "id_insegnante")
    private UUID id;

    @Column(nullable = false)
    private String biografia;
}
