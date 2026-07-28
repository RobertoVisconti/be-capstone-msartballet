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
public class Sala {

    @Id
    @GeneratedValue
    @Column(name = "id_sala")
    private UUID id;

    @Column(nullable = false)
    private String titolo;

    @Column(name = "img_sala", nullable = false)
    private String imgSala;

    @Column(name = "prezzo_affitto", nullable = false)
    private Double prezzoAffitto;

    public Sala(String titolo, String imgSala, Double prezzoAffitto) {
        this.titolo = titolo;
        this.imgSala = imgSala;
        this.prezzoAffitto = prezzoAffitto;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setImgSala(String imgSala) {
        this.imgSala = imgSala;
    }

    public void setPrezzoAffitto(Double prezzoAffitto) {
        this.prezzoAffitto = prezzoAffitto;
    }
}
