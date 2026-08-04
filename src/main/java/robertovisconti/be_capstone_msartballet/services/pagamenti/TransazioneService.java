package robertovisconti.be_capstone_msartballet.services.pagamenti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.*;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.pagamentoDTO.NewTransazioneDTO;
import robertovisconti.be_capstone_msartballet.repositories.corsi.CorsoRepository;
import robertovisconti.be_capstone_msartballet.repositories.pagamenti.TransazioneRepository;
import robertovisconti.be_capstone_msartballet.repositories.sale.SalaRepository;
import robertovisconti.be_capstone_msartballet.repositories.store.ProdottoRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;
import robertovisconti.be_capstone_msartballet.specification.TransazioneSpecification;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransazioneService {
    private final TransazioneRepository transazioneRepository;
    private final UtenteRepository utenteRepository;
    private final ProdottoRepository prodottoRepository;
    private final CorsoRepository corsoRepository;
    private final SalaRepository salaRepository;

    public TransazioneService(TransazioneRepository transazioneRepository, UtenteRepository utenteRepository, ProdottoRepository prodottoRepository, CorsoRepository corsoRepository, SalaRepository salaRepository) {
        this.transazioneRepository = transazioneRepository;
        this.utenteRepository = utenteRepository;
        this.prodottoRepository = prodottoRepository;
        this.corsoRepository = corsoRepository;
        this.salaRepository = salaRepository;
    }

    public Transazione creaTransazione(NewTransazioneDTO body) {
        Utente utente = trovaUtente(body.idUtente());
        Transazione nuovaTransazione;
        if (body.idProdotto() != null) {
            Prodotto prodotto = trovaProdotto(body.idProdotto());
            nuovaTransazione = new Transazione(prodotto.getPrezzoProdotto(), body.metodoPagamento());
            nuovaTransazione.setProdotto(prodotto);
        } else if (body.idCorso() != null) {
            Corso corso = trovaCorso(body.idCorso());
            nuovaTransazione = new Transazione(corso.getPrezzoMensile(), body.metodoPagamento());
            nuovaTransazione.setCorso(corso);
        } else {
            Sala sala = trovaSala(body.idSala());
            nuovaTransazione = new Transazione(sala.getPrezzoAffitto(), body.metodoPagamento());
            nuovaTransazione.setSala(sala);
        }

        nuovaTransazione.setUtente(utente);
        return transazioneRepository.save(nuovaTransazione);
    }

    public Page<Transazione> trovaConFiltri(UUID idUtente, UUID idProdotto, UUID idCorso, LocalDateTime dal, LocalDateTime al, Pageable pageable) {
        return transazioneRepository.findAll(TransazioneSpecification.filtra(idUtente, idProdotto, idCorso, dal, al), pageable);
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

    private Sala trovaSala(UUID idSala) {
        return salaRepository.findById(idSala)
                .orElseThrow(() -> new NotFoundException("Nessuna sala trovata con id " + idSala));
    }

    public Page<Transazione> trovaConFiltri(UUID idUtente, UUID idProdotto, UUID idCorso, UUID idSala, LocalDateTime dal, LocalDateTime al, Pageable pageable) {
        return transazioneRepository.findAll(TransazioneSpecification.filtra(idUtente, idProdotto, idCorso, idSala, dal, al), pageable);
    }
}
