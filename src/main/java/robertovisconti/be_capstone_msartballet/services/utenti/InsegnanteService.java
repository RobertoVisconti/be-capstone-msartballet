package robertovisconti.be_capstone_msartballet.services.utenti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Insegnante;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.repositories.utenti.InsegnanteRepository;

import java.util.UUID;

@Service
public class InsegnanteService {

    private final InsegnanteRepository insegnanteRepository;

    public InsegnanteService(InsegnanteRepository insegnanteRepository) {
        this.insegnanteRepository = insegnanteRepository;
    }

    public Page<Insegnante> trovaTutti(Pageable pageable) {
        return insegnanteRepository.findAll(pageable);
    }

    public Insegnante trovaPerId(UUID id) {
        return insegnanteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun insegnante trovato con id " + id));
    }
}
