package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.NoArgsConstructor;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id_admin")
@NoArgsConstructor
public class Admin extends Utente {
    public Admin(String nome, String cognome, String email, String password, LocalDate dataDiNascita) {
        super(nome, cognome, email, password, null, RuoloUtente.ADMIN, dataDiNascita);
        this.setAccountAttivo(true);
    }
}
