package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import robertovisconti.be_capstone_msartballet.enums.StatoPrenotazione;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Prenotazione {

    @Id
    @GeneratedValue
    @Column(name = "id_prenotazione")
    private UUID id;

    @Column(name = "stato_prenotazione")
    @Enumerated(EnumType.STRING)
    private StatoPrenotazione statoPrenotazione;

    @Column(name = "data_prenotazione")
    private LocalDateTime dataPrenotazione = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lezione", nullable = false)
    private Lezione lezione;

    public Prenotazione(StatoPrenotazione statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }

    public void setStatoPrenotazione(StatoPrenotazione statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setLezione(Lezione lezione) {
        this.lezione = lezione;
    }
}
