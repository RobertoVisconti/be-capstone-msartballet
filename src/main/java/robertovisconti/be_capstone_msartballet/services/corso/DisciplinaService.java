package robertovisconti.be_capstone_msartballet.services.corso;

import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Disciplina;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.corsoDTO.NewDisciplinaDTO;
import robertovisconti.be_capstone_msartballet.repositories.corsi.DisciplinaRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Disciplina creaDisciplina(NewDisciplinaDTO body) {
        Disciplina nuovaDisciplina = new Disciplina(body.nome(), body.descrizione());
        return disciplinaRepository.save(nuovaDisciplina);
    }

    public List<Disciplina> trovaTutte() {
        return disciplinaRepository.findAll();
    }

    public Disciplina trovaPerId(UUID id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessuna disciplina trovata con id " + id));
    }

    public Disciplina modificaDisciplina(UUID id, NewDisciplinaDTO body) {
        Disciplina disciplina = trovaPerId(id);
        disciplina.setNome(body.nome());
        disciplina.setDescrizione(body.descrizione());
        return disciplinaRepository.save(disciplina);
    }

    public void eliminaDisciplina(UUID id) {
        Disciplina disciplina = trovaPerId(id);
        disciplinaRepository.delete(disciplina);
    }
}
