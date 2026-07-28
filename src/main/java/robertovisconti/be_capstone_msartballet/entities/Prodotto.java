package robertovisconti.be_capstone_msartballet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Prodotto {

    @Id
    @GeneratedValue
    @Column(name = "id_prodotto")
    private UUID id;

    @Column(nullable = false)
    private String titolo;

    @Column(name = "descrizione_rodotto")
    private String descrizioneProdotto;

    @Column(name = "img_prodotto")
    private String imgProdotto;

    @Column(name = "prezzo_prodotto", nullable = false)
    private Double prezzoProdotto;

    public Prodotto(String titolo, String descrizioneProdotto, String imgProdotto, Double prezzoProdotto) {
        this.titolo = titolo;
        this.descrizioneProdotto = descrizioneProdotto;
        this.imgProdotto = imgProdotto;
        this.prezzoProdotto = prezzoProdotto;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizioneProdotto(String descrizioneProdotto) {
        this.descrizioneProdotto = descrizioneProdotto;
    }

    public void setImgProdotto(String imgProdotto) {
        this.imgProdotto = imgProdotto;
    }

    public void setPrezzoProdotto(Double prezzoProdotto) {
        this.prezzoProdotto = prezzoProdotto;
    }
}
