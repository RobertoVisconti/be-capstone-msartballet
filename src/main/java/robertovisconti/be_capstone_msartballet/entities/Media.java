package robertovisconti.be_capstone_msartballet.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import robertovisconti.be_capstone_msartballet.enums.TipoMedia;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Media {

    @Id
    @GeneratedValue
    @Column(name = "id_media")
    private UUID id;

    @Column(nullable = false)
    private String url;

    @Column(name = "tipo_media")
    @Enumerated(EnumType.STRING)
    private TipoMedia tipoMedia;

    private String titolo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_spettacolo", nullable = false)
    private Spettacolo spettacolo;

    public Media(String url, TipoMedia tipoMedia, String titolo) {
        this.url = url;
        this.tipoMedia = tipoMedia;
        this.titolo = titolo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTipoMedia(TipoMedia tipoMedia) {
        this.tipoMedia = tipoMedia;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setSpettacolo(Spettacolo spettacolo) {
        this.spettacolo = spettacolo;
    }
}
