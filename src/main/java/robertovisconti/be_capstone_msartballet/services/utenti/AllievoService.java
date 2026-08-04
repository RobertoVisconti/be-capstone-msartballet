package robertovisconti.be_capstone_msartballet.services.utenti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Allievo;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.repositories.utenti.AllievoRepository;
import robertovisconti.be_capstone_msartballet.specification.AllievoSpecification;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AllievoService {

    private final AllievoRepository allievoRepository;

    public AllievoService(AllievoRepository allievoRepository) {
        this.allievoRepository = allievoRepository;
    }

    public Page<Allievo> trovaConFiltri(String nome, String cognome, Boolean accountAttivo, LocalDate certificatoScadeEntro, Pageable pageable) {
        return allievoRepository.findAll(AllievoSpecification.filtra(nome, cognome, accountAttivo, certificatoScadeEntro), pageable);
    }

    public Allievo trovaPerId(UUID id) {
        return allievoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun allievo trovato con id " + id));
    }
}
