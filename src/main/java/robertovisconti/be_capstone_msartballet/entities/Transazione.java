package robertovisconti.be_capstone_msartballet.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Transazione {

    @Id
    @GeneratedValue
    @Column(name = "id_transazione")
    private UUID id;

    @Column(name = "data_transazione")
    private LocalDateTime data_transazione = LocalDateTime.now();

    @Column(nullable = false)
    private Double importo;

    @Column(name = "metodo_pagamento", nullable = false)
    private String metodoPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prodotto", nullable = false)
    private Prodotto prodotto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_corso")
    private Corso corso;

    public Transazione(Double importo, String metodoPagamento) {
        this.importo = importo;
        this.metodoPagamento = metodoPagamento;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }
}
