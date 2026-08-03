package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Lezione {

    @Id
    @GeneratedValue
    @Column(name = "id_lezione")
    private UUID id;

    @Column(name = "data_ora_inizio", nullable = false)
    private LocalDateTime dataOraInizio;

    @Column(name = "data_ora_fine", nullable = false)
    private LocalDateTime dataOraFine;

    @Column(name = "prezzo_lezione", nullable = false)
    private Double prezzoLezione;

    @ManyToOne
    @JoinColumn(name = "id_corso", nullable = false)
    private Corso corso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    public Lezione(LocalDateTime dataOraInizio, LocalDateTime dataOraFine, Double prezzoLezione) {
        this.dataOraInizio = dataOraInizio;
        this.dataOraFine = dataOraFine;
        this.prezzoLezione = prezzoLezione;
    }

    public void setDataOraInizio(LocalDateTime dataOraInizio) {
        this.dataOraInizio = dataOraInizio;
    }

    public void setDataOraFine(LocalDateTime dataOraFine) {
        this.dataOraFine = dataOraFine;
    }

    public void setPrezzoLezione(Double prezzoLezione) {
        this.prezzoLezione = prezzoLezione;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
