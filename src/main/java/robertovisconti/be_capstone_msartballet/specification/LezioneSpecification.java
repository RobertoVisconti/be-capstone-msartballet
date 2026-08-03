package robertovisconti.be_capstone_msartballet.specification;

import org.springframework.data.jpa.domain.Specification;
import robertovisconti.be_capstone_msartballet.entities.Lezione;

import java.time.LocalDateTime;
import java.util.UUID;

public class LezioneSpecification {

    private LezioneSpecification() {
    }

    public static Specification<Lezione> filtra(UUID idCorso, UUID idSala, LocalDateTime dal, LocalDateTime al) {
        return Specification.allOf(
                haIdCorso(idCorso),
                haIdSala(idSala),
                daData(dal),
                aData(al)
        );
    }

    private static Specification<Lezione> haIdCorso(UUID idCorso) {
        return (root, query, cb) -> idCorso == null ? null : cb.equal(root.get("corso").get("id"), idCorso);
    }

    private static Specification<Lezione> haIdSala(UUID idSala) {
        return (root, query, cb) -> idSala == null ? null : cb.equal(root.get("sala").get("id"), idSala);
    }

    private static Specification<Lezione> daData(LocalDateTime dal) {
        return (root, query, cb) -> dal == null ? null : cb.greaterThanOrEqualTo(root.get("dataOraInizio"), dal);
    }

    private static Specification<Lezione> aData(LocalDateTime al) {
        return (root, query, cb) -> al == null ? null : cb.lessThanOrEqualTo(root.get("dataOraInizio"), al);
    }
}
