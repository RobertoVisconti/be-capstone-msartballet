package robertovisconti.be_capstone_msartballet.specification;

import org.springframework.data.jpa.domain.Specification;
import robertovisconti.be_capstone_msartballet.entities.Prenotazione;
import robertovisconti.be_capstone_msartballet.enums.StatoPrenotazione;

import java.util.UUID;

public class PrenotazioneSpecification {

    private PrenotazioneSpecification() {
    }

    public static Specification<Prenotazione> filtra(UUID idUtente, UUID idLezione, StatoPrenotazione stato) {
        return Specification.allOf(
                haIdUtente(idUtente),
                haIdLezione(idLezione),
                haStato(stato)
        );
    }

    private static Specification<Prenotazione> haIdUtente(UUID idUtente) {
        return (root, query, cb) -> idUtente == null ? null : cb.equal(root.get("utente").get("id"), idUtente);
    }

    private static Specification<Prenotazione> haIdLezione(UUID idLezione) {
        return (root, query, cb) -> idLezione == null ? null : cb.equal(root.get("lezione").get("id"), idLezione);
    }

    private static Specification<Prenotazione> haStato(StatoPrenotazione stato) {
        return (root, query, cb) -> stato == null ? null : cb.equal(root.get("statoPrenotazione"), stato);
    }
}
