package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id_admin")
@NoArgsConstructor
@Getter
public class Admin extends Utente {

    private Boolean protetto = false;

    public Admin(String nome, String cognome, String email, String password, LocalDate dataDiNascita) {
        super(nome, cognome, email, password, null, RuoloUtente.ADMIN, dataDiNascita);
        this.setAccountAttivo(true);
        this.protetto = true;
    }
}
