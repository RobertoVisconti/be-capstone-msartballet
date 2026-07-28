package robertovisconti.be_capstone_msartballet.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import robertovisconti.be_capstone_msartballet.enums.LarghezzaPunte;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id_allievo")
@NoArgsConstructor
@Getter
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

    @Column(name = "data_scadenza_certificato", nullable = false)
    private LocalDate dataScadenzaCertificato;

    @Column(name = "contatto_emergenza_nome", nullable = false)
    private String contattoEmergenzaNome;

    @Column(name = "contatto_emergenza_telefono", nullable = false)
    private String contattoEmergenzaTelefono;

    public Allievo(String nome, String cognome, String email, String password, String imgProfilo, RuoloUtente ruolo, LocalDate dataDiNascita, String numeroScarpetta, String marcaScarpetta, Boolean haPunte, String marcaPunte, LarghezzaPunte larghezzaPunte, String tagliaBody, String tagliaCalzini, Integer altezzaCm, String tagliaPantalone, LocalDate dataScadenzaCertificato, String contattoEmergenzaNome, String contattoEmergenzaTelefono) {
        super(nome, cognome, email, password, imgProfilo, ruolo, dataDiNascita);
        this.numeroScarpetta = numeroScarpetta;
        this.marcaScarpetta = marcaScarpetta;
        this.haPunte = haPunte;
        this.marcaPunte = marcaPunte;
        this.larghezzaPunte = larghezzaPunte;
        this.tagliaBody = tagliaBody;
        this.tagliaCalzini = tagliaCalzini;
        this.altezzaCm = altezzaCm;
        this.tagliaPantalone = tagliaPantalone;
        this.dataScadenzaCertificato = dataScadenzaCertificato;
        this.contattoEmergenzaNome = contattoEmergenzaNome;
        this.contattoEmergenzaTelefono = contattoEmergenzaTelefono;
    }

    public void setNumeroScarpetta(String numeroScarpetta) {
        this.numeroScarpetta = numeroScarpetta;
    }

    public void setMarcaScarpetta(String marcaScarpetta) {
        this.marcaScarpetta = marcaScarpetta;
    }

    public void setHaPunte(Boolean haPunte) {
        this.haPunte = haPunte;
    }

    public void setMarcaPunte(String marcaPunte) {
        this.marcaPunte = marcaPunte;
    }

    public void setLarghezzaPunte(LarghezzaPunte larghezzaPunte) {
        this.larghezzaPunte = larghezzaPunte;
    }

    public void setTagliaBody(String tagliaBody) {
        this.tagliaBody = tagliaBody;
    }

    public void setTagliaCalzini(String tagliaCalzini) {
        this.tagliaCalzini = tagliaCalzini;
    }

    public void setAltezzaCm(Integer altezzaCm) {
        this.altezzaCm = altezzaCm;
    }

    public void setTagliaPantalone(String tagliaPantalone) {
        this.tagliaPantalone = tagliaPantalone;
    }

    public void setDataScadenzaCertificato(LocalDate dataScadenzaCertificato) {
        this.dataScadenzaCertificato = dataScadenzaCertificato;
    }

    public void setContattoEmergenzaNome(String contattoEmergenzaNome) {
        this.contattoEmergenzaNome = contattoEmergenzaNome;
    }

    public void setContattoEmergenzaTelefono(String contattoEmergenzaTelefono) {
        this.contattoEmergenzaTelefono = contattoEmergenzaTelefono;
    }
}
