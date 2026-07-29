package robertovisconti.be_capstone_msartballet.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import robertovisconti.be_capstone_msartballet.enums.LarghezzaPunte;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id_allievo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Allievo extends Utente {

    // calzatura mezza punta
    @Column(name = "numero_scarpetta")
    private String numeroScarpetta;

    @Column(name = "marca_scarpetta")
    private String marcaScarpetta;

    // calzatura da punte
    @Column(name = "ha_punte")
    private Boolean haPunte = false;

    @Column(name = "marca_punte")
    private String marcaPunte;

    @Column(name = "larghezza_punte")
    @Enumerated(EnumType.STRING)
    private LarghezzaPunte larghezzaPunte;

    // abbigliamento e sartoria
    @Column(name = "taglia_body")
    private String tagliaBody;

    @Column(name = "taglia_calzini")
    private String tagliaCalzini;

    @Column(name = "altezza_cm")
    private Integer altezzaCm;

    @Column(name = "taglia_pantalone")
    private String tagliaPantalone;

    // informazioni Mediche

    @Column(name = "data_scadenza_certificato")
    private LocalDate dataScadenzaCertificato;

    @Column(name = "contatto_emergenza_nome")
    private String contattoEmergenzaNome;

    @Column(name = "contatto_emergenza_telefono")
    private String contattoEmergenzaTelefono;

    // dati amministrativi
    @Column(name = "codice_fiscale")
    private String codiceFiscale;

    @Column(name = "quota_iscrizione_pagata")
    private Boolean quotaIscrizionePagata = false;

    @Column(name = "consenso_privacy_foto")
    private Boolean consensoPrivacyFoto = false;

    @Column(name = "note_segretieria", columnDefinition = "TEXT")
    private String noteSegreteria;
}
