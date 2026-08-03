package robertovisconti.be_capstone_msartballet.specification;

import org.springframework.data.jpa.domain.Specification;
import robertovisconti.be_capstone_msartballet.entities.Corso;
import robertovisconti.be_capstone_msartballet.enums.GiornoSettimana;
import robertovisconti.be_capstone_msartballet.enums.LivelloCorso;

import java.util.UUID;

public class CorsoSpecification {

    private CorsoSpecification() {
    }

    public static Specification<Corso> filtra(UUID idDisciplina, LivelloCorso livelloCorso, GiornoSettimana giornoSettimana, Double prezzoMin, Double prezzoMax, UUID idInsegnante) {
        return Specification.allOf(
                haIdDisciplina(idDisciplina),
                haLivello(livelloCorso),
                haGiorno(giornoSettimana),
                haPrezzoMinimo(prezzoMin),
                haPrezzoMassimo(prezzoMax),
                haIdInsegnante(idInsegnante)
        );
    }

    private static Specification<Corso> haIdDisciplina(UUID idDisciplina) {
        return (root, query, cb) -> idDisciplina == null ? null : cb.equal(root.get("disciplina").get("id"), idDisciplina);
    }

    private static Specification<Corso> haLivello(LivelloCorso livelloCorso) {
        return (root, query, cb) -> livelloCorso == null ? null : cb.equal(root.get("livelloCorso"), livelloCorso);
    }

    private static Specification<Corso> haGiorno(GiornoSettimana giornoSettimana) {
        return (root, query, cb) -> giornoSettimana == null ? null : cb.equal(root.get("giornoSettimana"), giornoSettimana);
    }

    private static Specification<Corso> haPrezzoMinimo(Double prezzoMin) {
        return (root, query, cb) -> prezzoMin == null ? null : cb.greaterThanOrEqualTo(root.get("prezzoMensile"), prezzoMin);
    }

    private static Specification<Corso> haPrezzoMassimo(Double prezzoMax) {
        return (root, query, cb) -> prezzoMax == null ? null : cb.lessThanOrEqualTo(root.get("prezzoMensile"), prezzoMax);
    }

    private static Specification<Corso> haIdInsegnante(UUID idInsegnante) {
        return (root, query, cb) -> idInsegnante == null ? null : cb.equal(root.get("insegnante").get("id"), idInsegnante);
    }
}
