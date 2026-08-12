package robertovisconti.be_capstone_msartballet.services.lezione;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Lezione;
import robertovisconti.be_capstone_msartballet.entities.Prenotazione;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;
import robertovisconti.be_capstone_msartballet.enums.StatoPrenotazione;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.CambiaStatoPrenotazioneDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.NewPrenotazioneDTO;
import robertovisconti.be_capstone_msartballet.repositories.lezioni.LezioneRepository;
import robertovisconti.be_capstone_msartballet.repositories.lezioni.PrenotazioneRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;
import robertovisconti.be_capstone_msartballet.specification.PrenotazioneSpecification;

import java.util.UUID;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteRepository utenteRepository;
    private final LezioneRepository lezioneRepository;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, UtenteRepository utenteRepository, LezioneRepository lezioneRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.utenteRepository = utenteRepository;
        this.lezioneRepository = lezioneRepository;
    }

    public Prenotazione creaPrenotazione(NewPrenotazioneDTO body, Utente richiedente) {
        UUID idUtenteEffettivo = richiedente.getRuolo() == RuoloUtente.ADMIN ? body.idUtente() : richiedente.getId();
        Utente utente = trovaUtente(idUtenteEffettivo);
        Lezione lezione = trovaLezione(body.idLezione());
        Prenotazione nuovaPrenotazione = new Prenotazione(StatoPrenotazione.IN_ATTESA);
        nuovaPrenotazione.setUtente(utente);
        nuovaPrenotazione.setLezione(lezione);
        return prenotazioneRepository.save(nuovaPrenotazione);
    }

    public Page<Prenotazione> trovaConFiltri(UUID idUtente, UUID idLezione, StatoPrenotazione stato, Pageable pageable) {
        return prenotazioneRepository.findAll(PrenotazioneSpecification.filtra(idUtente, idLezione, stato), pageable);
    }

    public Prenotazione trovaPerId(UUID id) {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessuna prenotazione trovata con id " + id));
    }

    public Prenotazione cambiaStato(UUID id, CambiaStatoPrenotazioneDTO body) {
        Prenotazione prenotazione = trovaPerId(id);
        prenotazione.setStatoPrenotazione(body.statoPrenotazione());
        return prenotazioneRepository.save(prenotazione);
    }

    public void eliminaPrenotazione(UUID id) {
        prenotazioneRepository.delete(trovaPerId(id));
    }

    private Utente trovaUtente(UUID idUtente) {
        return utenteRepository.findById(idUtente)
                .orElseThrow(() -> new NotFoundException("Nessun utente trovato con id " + idUtente));
    }

    private Lezione trovaLezione(UUID idLezione) {
        return lezioneRepository.findById(idLezione)
                .orElseThrow(() -> new NotFoundException("Nessuna lezione trovata con id " + idLezione));
    }
}
