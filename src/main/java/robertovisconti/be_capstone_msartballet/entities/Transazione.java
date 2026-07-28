package robertovisconti.be_capstone_msartballet.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
}
