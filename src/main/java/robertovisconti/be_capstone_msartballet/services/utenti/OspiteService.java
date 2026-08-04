package robertovisconti.be_capstone_msartballet.services.utenti;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Ospite;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.repositories.utenti.OspiteRepository;

import java.util.UUID;

@Service
public class OspiteService {

    private final OspiteRepository ospiteRepository;

    public OspiteService(OspiteRepository ospiteRepository) {
        this.ospiteRepository = ospiteRepository;
    }

    public Page<Ospite> trovaTutti(Pageable pageable) {
        return ospiteRepository.findAll(pageable);
    }

    public Ospite trovaPerId(UUID id) {
        return ospiteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun ospite trovato con id " + id));
    }
}
