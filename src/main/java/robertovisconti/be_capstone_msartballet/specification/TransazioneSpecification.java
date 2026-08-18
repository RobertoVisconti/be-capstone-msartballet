package robertovisconti.be_capstone_msartballet.specification;

import org.springframework.data.jpa.domain.Specification;
import robertovisconti.be_capstone_msartballet.entities.Transazione;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransazioneSpecification {

    private TransazioneSpecification() {
    }

    public static Specification<Transazione> filtra(UUID idUtente, UUID idProdotto, UUID idCorso, UUID idSala, LocalDateTime dal, LocalDateTime al) {
        return Specification.allOf(
                haIdUtente(idUtente),
                haIdProdotto(idProdotto),
                haIdCorso(idCorso),
                haIdSala(idSala),
                daData(dal),
                aData(al)
        );
    }

    private static Specification<Transazione> haIdUtente(UUID idUtente) {
        return (root, query, cb) -> idUtente == null ? null : cb.equal(root.get("utente").get("id"), idUtente);
    }

    private static Specification<Transazione> haIdProdotto(UUID idProdotto) {
        return (root, query, cb) -> idProdotto == null ? null : cb.equal(root.get("prodotto").get("id"), idProdotto);
    }

    private static Specification<Transazione> haIdCorso(UUID idCorso) {
        return (root, query, cb) -> idCorso == null ? null : cb.equal(root.get("corso").get("id"), idCorso);
    }

    private static Specification<Transazione> haIdSala(UUID idSala) {
        return (root, query, cb) -> idSala == null ? null : cb.equal(root.get("sala").get("id"), idSala);
    }

    private static Specification<Transazione> daData(LocalDateTime dal) {
        return (root, query, cb) -> dal == null ? null : cb.greaterThanOrEqualTo(root.get("data_transazione"), dal);
    }

    private static Specification<Transazione> aData(LocalDateTime al) {
        return (root, query, cb) -> al == null ? null : cb.lessThanOrEqualTo(root.get("data_transazione"), al);
    }


}
