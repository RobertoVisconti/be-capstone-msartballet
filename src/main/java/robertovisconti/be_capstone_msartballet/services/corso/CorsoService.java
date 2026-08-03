package robertovisconti.be_capstone_msartballet.services.corso;

import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Corso;
import robertovisconti.be_capstone_msartballet.entities.Disciplina;
import robertovisconti.be_capstone_msartballet.entities.Insegnante;
import robertovisconti.be_capstone_msartballet.enums.GiornoSettimana;
import robertovisconti.be_capstone_msartballet.enums.LivelloCorso;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.corsoDTO.NewCorsoDTO;
import robertovisconti.be_capstone_msartballet.repositories.corsi.CorsoRepository;
import robertovisconti.be_capstone_msartballet.repositories.corsi.DisciplinaRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.InsegnanteRepository;
import robertovisconti.be_capstone_msartballet.specification.CorsoSpecification;

import java.util.List;
import java.util.UUID;

@Service
public class CorsoService {

    private final CorsoRepository corsoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final InsegnanteRepository insegnanteRepository;

    public CorsoService(CorsoRepository corsoRepository, DisciplinaRepository disciplinaRepository, InsegnanteRepository insegnanteRepository) {
        this.corsoRepository = corsoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.insegnanteRepository = insegnanteRepository;
    }

    public Corso creaCorso(NewCorsoDTO body) {

        Disciplina disciplina = trovaDisciplina(body.idDisciplina());
        Insegnante insegnante = trovaInsegnante(body.idInsegnante());

        Corso nuovoCorso = new Corso(body.titolo(), body.descrizione(), body.livelloCorso(), body.giornoSettimana(), body.oraInizio(), body.oraFine(), body.prezzoMensile());

        nuovoCorso.setDisciplina(disciplina);
        nuovoCorso.setInsegnante(insegnante);

        return corsoRepository.save(nuovoCorso);
    }

    public List<Corso> trovaConFiltri(UUID idDisciplina, LivelloCorso livelloCorso, GiornoSettimana giornoSettimana, Double prezzoMin, Double prezzoMax, UUID idInsegnante) {
        return corsoRepository.findAll(CorsoSpecification.filtra(idDisciplina, livelloCorso, giornoSettimana, prezzoMin, prezzoMax, idInsegnante));
    }

    public Corso trovaPerId(UUID id) {
        return corsoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun corso trovato con id " + id));
    }

    public Corso modificaCorso(UUID id, NewCorsoDTO body) {
        Corso corso = trovaPerId(id);
        corso.setTitolo(body.titolo());
        corso.setDescrizione(body.descrizione());
        corso.setLivelloCorso(body.livelloCorso());
        corso.setGiornoSettimana(body.giornoSettimana());
        corso.setOraInizio(body.oraInizio());
        corso.setOra_fine(body.oraFine());
        corso.setPrezzoMensile(body.prezzoMensile());
        corso.setDisciplina(trovaDisciplina(body.idDisciplina()));
        corso.setInsegnante((trovaInsegnante(body.idInsegnante())));
        return corsoRepository.save(corso);
    }

    public void eliminaCorso(UUID id) {
        corsoRepository.delete(trovaPerId(id));
    }

    private Disciplina trovaDisciplina(UUID idDisciplina) {
        return disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new NotFoundException("Nessuna disciplina trovata con id " + idDisciplina));
    }

    private Insegnante trovaInsegnante(UUID idInsegnante) {
        return insegnanteRepository.findById(idInsegnante)
                .orElseThrow(() -> new NotFoundException("Nessun insegnante trovato con id " + idInsegnante));
    }
}
