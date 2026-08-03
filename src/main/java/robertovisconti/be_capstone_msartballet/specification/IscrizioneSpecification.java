package robertovisconti.be_capstone_msartballet.specification;

import org.springframework.data.jpa.domain.Specification;
import robertovisconti.be_capstone_msartballet.entities.Iscrizione;
import robertovisconti.be_capstone_msartballet.enums.StatoIscrizione;

import java.util.UUID;

public class IscrizioneSpecification {

    private IscrizioneSpecification() {
    }

    public static Specification<Iscrizione> filtra(UUID idAllievo, UUID idCorso, StatoIscrizione stato) {
        return Specification.allOf(
                haIdAllievo(idAllievo),
                haIdCorso(idCorso),
                haStato(stato)
        );
    }

    private static Specification<Iscrizione> haIdAllievo(UUID idAllievo) {
        return (root, query, cb) -> idAllievo == null ? null : cb.equal(root.get("allievo").get("id"), idAllievo);
    }

    private static Specification<Iscrizione> haIdCorso(UUID idCorso) {
        return (root, query, cb) -> idCorso == null ? null : cb.equal(root.get("corso").get("id"), idCorso);
    }

    private static Specification<Iscrizione> haStato(StatoIscrizione stato) {
        return (root, query, cb) -> stato == null ? null : cb.equal(root.get("stato"), stato);
    }
}
