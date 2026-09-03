package robertovisconti.be_capstone_msartballet.services.lezione;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Lezione;
import robertovisconti.be_capstone_msartballet.entities.Ospite;
import robertovisconti.be_capstone_msartballet.entities.Prenotazione;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;
import robertovisconti.be_capstone_msartballet.enums.StatoPrenotazione;
import robertovisconti.be_capstone_msartballet.exceptions.BadRequestException;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.CambiaStatoPrenotazioneDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.NewPrenotazioneDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.NewPrenotazioneOspiteDTO;
import robertovisconti.be_capstone_msartballet.repositories.lezioni.LezioneRepository;
import robertovisconti.be_capstone_msartballet.repositories.lezioni.PrenotazioneRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;
import robertovisconti.be_capstone_msartballet.services.utenti.OspiteService;
import robertovisconti.be_capstone_msartballet.specification.PrenotazioneSpecification;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteRepository utenteRepository;
    private final LezioneRepository lezioneRepository;
    private final OspiteService ospiteService;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, UtenteRepository utenteRepository, LezioneRepository lezioneRepository, OspiteService ospiteService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.utenteRepository = utenteRepository;
        this.lezioneRepository = lezioneRepository;
        this.ospiteService = ospiteService;
    }

    public Prenotazione creaPrenotazione(NewPrenotazioneDTO body, Utente richiedente) {
        UUID idUtenteEffettivo = richiedente.getRuolo() == RuoloUtente.ADMIN ? body.idUtente() : richiedente.getId();
        Utente utente = trovaUtente(idUtenteEffettivo);
        Lezione lezione = trovaLezione(body.idLezione());
        verificaNonGiaPrenotata(utente.getId(), lezione.getId());
        Prenotazione nuovaPrenotazione = new Prenotazione(StatoPrenotazione.IN_ATTESA);
        nuovaPrenotazione.setUtente(utente);
        nuovaPrenotazione.setLezione(lezione);
        return prenotazioneRepository.save(nuovaPrenotazione);
    }

    public Prenotazione creaPrenotazioneOspite(NewPrenotazioneOspiteDTO body) {
        Ospite ospite = ospiteService.trovaOCrea(body.nome(), body.cognome(), body.email(), body.telefono());
        Lezione lezione = trovaLezione(body.idLezione());
        verificaNonGiaPrenotata(ospite.getId(), lezione.getId());
        Prenotazione nuovaPrenotazione = new Prenotazione(StatoPrenotazione.IN_ATTESA);
        nuovaPrenotazione.setUtente(ospite);
        nuovaPrenotazione.setLezione(lezione);
        return prenotazioneRepository.save(nuovaPrenotazione);
    }

    public Page<Prenotazione> trovaConFiltri(UUID idUtente, UUID idLezione, StatoPrenotazione stato, UUID idCorso, LocalDate dataDa, LocalDate dataA, Pageable pageable) {
        return prenotazioneRepository.findAll(PrenotazioneSpecification.filtra(idUtente, idLezione, stato, idCorso, dataDa, dataA), pageable);
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

    private void verificaNonGiaPrenotata(UUID idUtente, UUID idLezione) {
        if (prenotazioneRepository.existsByUtente_IdAndLezione_Id(idUtente, idLezione)) {
            throw new BadRequestException("Hai già una prenotazione per questa lezione.");
        }
    }
}
