package robertovisconti.be_capstone_msartballet.services.lezione;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Corso;
import robertovisconti.be_capstone_msartballet.entities.Lezione;
import robertovisconti.be_capstone_msartballet.entities.Sala;
import robertovisconti.be_capstone_msartballet.exceptions.BadRequestException;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.NewLezioneDTO;
import robertovisconti.be_capstone_msartballet.repositories.corsi.CorsoRepository;
import robertovisconti.be_capstone_msartballet.repositories.lezioni.LezioneRepository;
import robertovisconti.be_capstone_msartballet.repositories.sale.SalaRepository;
import robertovisconti.be_capstone_msartballet.specification.LezioneSpecification;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LezioneService {

    private final LezioneRepository lezioneRepository;
    private final CorsoRepository corsoRepository;
    private final SalaRepository salaRepository;

    public LezioneService(LezioneRepository lezioneRepository, CorsoRepository corsoRepository, SalaRepository salaRepository) {
        this.lezioneRepository = lezioneRepository;
        this.corsoRepository = corsoRepository;
        this.salaRepository = salaRepository;
    }

    public Lezione creaLezione(NewLezioneDTO body) {
        Corso corso = trovaCorso(body.idCorso());
        Sala sala = trovaSala(body.idSala());
        verificaDisponibilitaSala(sala.getId(), body.dataOraInizio(), body.dataOraFine(), null);
        Lezione nuovaLezione = new Lezione(body.dataOraInizio(), body.dataOraFine(), body.prezzoLezione());
        nuovaLezione.setCorso(corso);
        nuovaLezione.setSala(sala);
        return lezioneRepository.save(nuovaLezione);
    }

    public Page<Lezione> trovaConFiltri(UUID idCorso, UUID idSala, LocalDateTime dal, LocalDateTime al, Pageable pageable) {
        return lezioneRepository.findAll(LezioneSpecification.filtra(idCorso, idSala, dal, al), pageable);
    }

    public Lezione trovaPerId(UUID id) {
        return lezioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessuna lezione trovata con id " + id));
    }

    public Lezione modificaLezione(UUID id, NewLezioneDTO body) {
        Lezione lezione = trovaPerId(id);
        Sala sala = trovaSala(body.idSala());
        verificaDisponibilitaSala(sala.getId(), body.dataOraInizio(), body.dataOraFine(), id);
        lezione.setDataOraInizio(body.dataOraInizio());
        lezione.setDataOraFine(body.dataOraFine());
        lezione.setPrezzoLezione(body.prezzoLezione());
        lezione.setCorso(trovaCorso(body.idCorso()));
        lezione.setSala(sala);
        return lezioneRepository.save(lezione);
    }

    public void eliminaLezione(UUID id) {
        lezioneRepository.delete(trovaPerId(id));
    }

    private Corso trovaCorso(UUID idCorso) {
        return corsoRepository.findById(idCorso)
                .orElseThrow(() -> new NotFoundException("Nessun corso trovato con id " + idCorso));
    }

    private Sala trovaSala(UUID idSala) {
        return salaRepository.findById(idSala)
                .orElseThrow(() -> new NotFoundException("Nessuna sala trovata con id " + idSala));
    }

    private void verificaDisponibilitaSala(UUID idSala, LocalDateTime inizio, LocalDateTime fine, UUID idLezioneEsclusa) {
        boolean sovrapposta = lezioneRepository
                .findBySala_IdAndDataOraInizioLessThanAndDataOraFineGreaterThan(idSala, fine, inizio)
                .stream()
                .anyMatch(l -> !l.getId().equals(idLezioneEsclusa));
        if (sovrapposta) {
            throw new BadRequestException("La sala scelta è già occupata da un'altra lezione in questo orario");
        }
    }
}
