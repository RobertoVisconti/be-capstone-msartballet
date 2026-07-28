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
public class Disciplina {

    @Id
    @GeneratedValue
    @Column(name = "id_disciplina")
    private UUID id;

    @Column(nullable = false)
    private String nome;

    private String descrizione;

    public Disciplina(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
