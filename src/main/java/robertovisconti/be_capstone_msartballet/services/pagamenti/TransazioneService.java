package robertovisconti.be_capstone_msartballet.services.pagamenti;

import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Corso;
import robertovisconti.be_capstone_msartballet.entities.Prodotto;
import robertovisconti.be_capstone_msartballet.entities.Transazione;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.pagamentoDTO.NewTransazioneDTO;
import robertovisconti.be_capstone_msartballet.repositories.corsi.CorsoRepository;
import robertovisconti.be_capstone_msartballet.repositories.pagamenti.TransazioneRepository;
import robertovisconti.be_capstone_msartballet.repositories.store.ProdottoRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;
import robertovisconti.be_capstone_msartballet.specification.TransazioneSpecification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransazioneService {
    private final TransazioneRepository transazioneRepository;
    private final UtenteRepository utenteRepository;
    private final ProdottoRepository prodottoRepository;
    private final CorsoRepository corsoRepository;

    public TransazioneService(TransazioneRepository transazioneRepository, UtenteRepository utenteRepository, ProdottoRepository prodottoRepository, CorsoRepository corsoRepository) {
        this.transazioneRepository = transazioneRepository;
        this.utenteRepository = utenteRepository;
        this.prodottoRepository = prodottoRepository;
        this.corsoRepository = corsoRepository;
    }

    public Transazione creaTransazione(NewTransazioneDTO body) {
        Utente utente = trovaUtente(body.idUtente());
        Prodotto prodotto = trovaProdotto(body.idProdotto());
        Corso corso = body.idCorso() != null ? trovaCorso(body.idCorso()) : null;
        Transazione nuovaTransazione = new Transazione(body.importo(), body.metodoPagamento());
        nuovaTransazione.setUtente(utente);
        nuovaTransazione.setProdotto(prodotto);
        nuovaTransazione.setCorso(corso);
        return transazioneRepository.save(nuovaTransazione);
    }

    public List<Transazione> trovaConFiltri(UUID idUtente, UUID idProdotto, UUID idCorso, LocalDateTime dal, LocalDateTime al) {
        return transazioneRepository.findAll(TransazioneSpecification.filtra(idUtente, idProdotto, idCorso, dal, al));
    }

    public Transazione trovaPerId(UUID id) {
        return transazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessuna transazione trovata con id " + id));
    }

    public void eliminaTransazione(UUID id) {
        transazioneRepository.delete(trovaPerId(id));
    }

    private Utente trovaUtente(UUID idUtente) {
        return utenteRepository.findById(idUtente)
                .orElseThrow(() -> new NotFoundException("Nessun utente trovato con id " + idUtente));
    }

    private Prodotto trovaProdotto(UUID idProdotto) {
        return prodottoRepository.findById(idProdotto)
                .orElseThrow(() -> new NotFoundException("Nessun prodotto trovato con id " + idProdotto));
    }

    private Corso trovaCorso(UUID idCorso) {
        return corsoRepository.findById(idCorso)
                .orElseThrow(() -> new NotFoundException("Nessun corso trovato con id " + idCorso));
    }
}
