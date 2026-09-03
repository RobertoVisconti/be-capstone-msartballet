package robertovisconti.be_capstone_msartballet.specification;

import org.springframework.data.jpa.domain.Specification;
import robertovisconti.be_capstone_msartballet.entities.Prenotazione;
import robertovisconti.be_capstone_msartballet.enums.StatoPrenotazione;

import java.time.LocalDate;
import java.util.UUID;

public class PrenotazioneSpecification {

    private PrenotazioneSpecification() {
    }

    public static Specification<Prenotazione> filtra(UUID idUtente, UUID idLezione, StatoPrenotazione stato, UUID idCorso, LocalDate dataDa, LocalDate dataA) {
        return Specification.allOf(
                haIdUtente(idUtente),
                haIdLezione(idLezione),
                haStato(stato),
                haIdCorso(idCorso),
                haDataDa(dataDa),
                haDataA(dataA)
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

    private static Specification<Prenotazione> haIdCorso(UUID idCorso) {
        return (root, query, cb) -> idCorso == null ? null : cb.equal(root.get("lezione").get("corso").get("id"), idCorso);
    }

    private static Specification<Prenotazione> haDataDa(LocalDate dataDa) {
        return (root, query, cb) -> dataDa == null ? null : cb.greaterThanOrEqualTo(root.get("lezione").get("dataOraInizio"), dataDa.atStartOfDay());
    }

    private static Specification<Prenotazione> haDataA(LocalDate dataA) {
        return (root, query, cb) -> dataA == null ? null : cb.lessThanOrEqualTo(root.get("lezione").get("dataOraInizio"), dataA.atTime(23, 59, 59));
    }
}
