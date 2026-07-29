package robertovisconti.be_capstone_msartballet.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Spettacolo {

    @Id
    @GeneratedValue
    @Column(name = "id_spettacolo")
    private UUID id;

    @Column(nullable = false)
    private String titolo;


    private String descrizione;

    @Column(name = "data_evento")
    private LocalDate dataEvento;

    private String luogo;

    public Spettacolo(String titolo, String descrizione, LocalDate dataEvento, String luogo) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataEvento = dataEvento;
        this.luogo = luogo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }
}
