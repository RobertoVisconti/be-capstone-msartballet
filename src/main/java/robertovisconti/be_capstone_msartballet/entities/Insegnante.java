package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id_insegnante")
@NoArgsConstructor
@Getter
public class Insegnante extends Utente {

    @Column(nullable = false)
    private String biografia;

    public Insegnante(String nome, String cognome, String email, String password, String imgProfilo, RuoloUtente ruolo, LocalDate dataDiNascita, String biografia) {
        super(nome, cognome, email, password, imgProfilo, ruolo, dataDiNascita);
        this.biografia = biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
}
