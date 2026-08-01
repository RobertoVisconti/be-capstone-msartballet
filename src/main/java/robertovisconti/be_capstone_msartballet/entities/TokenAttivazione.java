package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class TokenAttivazione {

    @Id
    @GeneratedValue
    @Column(name = "id_token_attivazione")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDateTime dataScadenza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @Column(nullable = false)
    private Boolean utilizzato = false;

    public TokenAttivazione(String token, LocalDateTime dataScadenza, Utente utente) {
        this.token = token;
        this.dataScadenza = dataScadenza;
        this.utente = utente;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setDataScadenza(LocalDateTime dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setUtilizzato(Boolean utilizzato) {
        this.utilizzato = utilizzato;
    }
}
