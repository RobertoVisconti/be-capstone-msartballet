package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import robertovisconti.be_capstone_msartballet.enums.GiornoSettimana;
import robertovisconti.be_capstone_msartballet.enums.LivelloCorso;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Corso {

    @Id
    @GeneratedValue
    @Column(name = "id_corso")
    private UUID id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;

    @Column(name = "livello_corso", nullable = false)
    @Enumerated(EnumType.STRING)
    private LivelloCorso livelloCorso;

    @Column(name = "giorno_settimana", nullable = false)
    @Enumerated(EnumType.STRING)
    private GiornoSettimana giornoSettimana;

    @Column(name = "ora_inizio", nullable = false)
    private LocalTime oraInizio;

    @Column(name = "ora_fine", nullable = false)
    private LocalTime ora_fine;

    @Column(name = "prezzo_mensile", nullable = false)
    private Double prezzoMensile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina;

    public Corso(String titolo, String descrizione, LivelloCorso livelloCorso, GiornoSettimana giornoSettimana, LocalTime oraInizio, LocalTime ora_fine, Double prezzoMensile) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.livelloCorso = livelloCorso;
        this.giornoSettimana = giornoSettimana;
        this.oraInizio = oraInizio;
        this.ora_fine = ora_fine;
        this.prezzoMensile = prezzoMensile;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setLivelloCorso(LivelloCorso livelloCorso) {
        this.livelloCorso = livelloCorso;
    }

    public void setGiornoSettimana(GiornoSettimana giornoSettimana) {
        this.giornoSettimana = giornoSettimana;
    }

    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
    }

    public void setOra_fine(LocalTime ora_fine) {
        this.ora_fine = ora_fine;
    }

    public void setPrezzoMensile(Double prezzoMensile) {
        this.prezzoMensile = prezzoMensile;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
