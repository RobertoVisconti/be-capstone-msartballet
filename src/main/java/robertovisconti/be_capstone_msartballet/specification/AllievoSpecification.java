package robertovisconti.be_capstone_msartballet.specification;

import org.springframework.data.jpa.domain.Specification;
import robertovisconti.be_capstone_msartballet.entities.Allievo;

import java.time.LocalDate;

public class AllievoSpecification {

    private AllievoSpecification() {
    }

    public static Specification<Allievo> filtra(String nome, String cognome, Boolean accountAttivo, LocalDate certificatoScadeEntro) {
        return Specification.allOf(
                haNome(nome),
                haCognome(cognome),
                haAccountAttivo(accountAttivo),
                haCertificatoScadeEntro(certificatoScadeEntro)
        );
    }

    private static Specification<Allievo> haNome(String nome) {
        return (root, query, cb) -> nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    private static Specification<Allievo> haCognome(String cognome) {
        return (root, query, cb) -> cognome == null ? null : cb.like(cb.lower(root.get("cognome")), "%" + cognome.toLowerCase() + "%");
    }

    private static Specification<Allievo> haAccountAttivo(Boolean accountAttivo) {
        return (root, query, cb) -> accountAttivo == null ? null : cb.equal(root.get("accountAttivo"), accountAttivo);
    }

    private static Specification<Allievo> haCertificatoScadeEntro(LocalDate certificatoScadeEntro) {
        return (root, query, cb) -> certificatoScadeEntro == null ? null : cb.lessThanOrEqualTo(root.get("dataScadenzaCertificato"), certificatoScadeEntro);
    }
}
