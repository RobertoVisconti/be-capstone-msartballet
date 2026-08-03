package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import robertovisconti.be_capstone_msartballet.enums.StatoIscrizione;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Iscrizione {

    @Id
    @GeneratedValue
    @Column(name = "id_iscrizione")
    private UUID id;

    @Column(name = "data_iscrizione")
    private LocalDateTime dataIscrizione = LocalDateTime.now();

    @Column(name = "stato_iscrizione")
    @Enumerated(EnumType.STRING)
    private StatoIscrizione stato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_allievo", nullable = false)
    private Allievo allievo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_corso", nullable = false)
    private Corso corso;

    public void setStato(StatoIscrizione stato) {
        this.stato = stato;
    }

    public void setAllievo(Allievo allievo) {
        this.allievo = allievo;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }
}
