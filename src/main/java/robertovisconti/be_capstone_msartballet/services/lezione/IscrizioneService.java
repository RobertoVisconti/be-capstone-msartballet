package robertovisconti.be_capstone_msartballet.services.lezione;


import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Allievo;
import robertovisconti.be_capstone_msartballet.entities.Corso;
import robertovisconti.be_capstone_msartballet.entities.Iscrizione;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;
import robertovisconti.be_capstone_msartballet.enums.StatoIscrizione;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.CambiaStatoIscrizioneDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.NewIscrizioneDTO;
import robertovisconti.be_capstone_msartballet.repositories.corsi.CorsoRepository;
import robertovisconti.be_capstone_msartballet.repositories.lezioni.IscrizioneRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.AllievoRepository;
import robertovisconti.be_capstone_msartballet.specification.IscrizioneSpecification;

import java.util.List;
import java.util.UUID;

@Service
public class IscrizioneService {

    private final IscrizioneRepository iscrizioneRepository;
    private final AllievoRepository allievoRepository;
    private final CorsoRepository corsoRepository;

    public IscrizioneService(IscrizioneRepository iscrizioneRepository, AllievoRepository allievoRepository, CorsoRepository corsoRepository) {
        this.iscrizioneRepository = iscrizioneRepository;
        this.allievoRepository = allievoRepository;
        this.corsoRepository = corsoRepository;
    }

    public Iscrizione creaIscrizione(NewIscrizioneDTO body, Utente richiedente) {
        UUID idAllievoEffettivo = richiedente.getRuolo() == RuoloUtente.ADMIN ? body.idAllievo() : richiedente.getId();
        Allievo allievo = trovaAllievo(idAllievoEffettivo);
        Corso corso = trovaCorso(body.idCorso());
        Iscrizione nuovaIscrizione = new Iscrizione();
        nuovaIscrizione.setAllievo(allievo);
        nuovaIscrizione.setCorso(corso);
        nuovaIscrizione.setStato(StatoIscrizione.ATTIVA);
        return iscrizioneRepository.save(nuovaIscrizione);
    }

    public List<Iscrizione> trovaConFiltri(UUID idAllievo, UUID idCorso, StatoIscrizione stato) {
        return iscrizioneRepository.findAll(IscrizioneSpecification.filtra(idAllievo, idCorso, stato));
    }

    public Iscrizione trovaPerId(UUID id) {
        return iscrizioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessuna iscrizione trovata con id " + id));
    }

    public Iscrizione cambiaStato(UUID id, CambiaStatoIscrizioneDTO body) {
        Iscrizione iscrizione = trovaPerId(id);
        iscrizione.setStato(body.stato());
        return iscrizioneRepository.save(iscrizione);
    }

    public void eliminaIscrizione(UUID id) {
        iscrizioneRepository.delete(trovaPerId(id));
    }

    private Allievo trovaAllievo(UUID idAllievo) {
        return allievoRepository.findById(idAllievo)
                .orElseThrow(() -> new NotFoundException("Nessun allievo trovato con id " + idAllievo));
    }

    private Corso trovaCorso(UUID idCorso) {
        return corsoRepository.findById(idCorso)
                .orElseThrow(() -> new NotFoundException("Nessun corso trovato con id " + idCorso));
    }
}
