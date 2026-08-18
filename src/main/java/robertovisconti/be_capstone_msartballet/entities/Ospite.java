package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id_ospite")
@NoArgsConstructor
@Getter
public class Ospite extends Utente {

    private String telefono;

    public Ospite(String nome, String cognome, String email, LocalDate dataDiNascita) {
        super(nome, cognome, email, null, null, RuoloUtente.OSPITE, dataDiNascita);
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
