package robertovisconti.be_capstone_msartballet.services.utenti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Allievo;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;
import robertovisconti.be_capstone_msartballet.exceptions.BadRequestException;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.AggiornaAllievoDTO;
import robertovisconti.be_capstone_msartballet.repositories.utenti.AllievoRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;
import robertovisconti.be_capstone_msartballet.specification.AllievoSpecification;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AllievoService {

    private final AllievoRepository allievoRepository;
    private final UtenteRepository utenteRepository;

    public AllievoService(AllievoRepository allievoRepository, UtenteRepository utenteRepository) {
        this.allievoRepository = allievoRepository;
        this.utenteRepository = utenteRepository;
    }

    public Page<Allievo> trovaConFiltri(String nome, String cognome, Boolean accountAttivo, LocalDate certificatoScadeEntro, Pageable pageable) {
        return allievoRepository.findAll(AllievoSpecification.filtra(nome, cognome, accountAttivo, certificatoScadeEntro), pageable);
    }

    public Allievo trovaPerId(UUID id) {
        return allievoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun allievo trovato con id " + id));
    }

    public Allievo modificaAllievo(UUID id, AggiornaAllievoDTO body, Utente richiedente) {
        Allievo allievo = trovaPerId(id);
        boolean isAdmin = richiedente.getRuolo() == RuoloUtente.ADMIN;

        if (!isAdmin && !allievo.getId().equals(richiedente.getId())) {
            throw new AccessDeniedException("Non puoi modificare il profilo di un altro allievo");
        }

        if (isAdmin) {
            if (!allievo.getEmail().equals(body.email()) && utenteRepository.existsByEmail(body.email())) {
                throw new BadRequestException("L'email " + body.email() + " è già in uso");
            }
            allievo.setNome(body.nome());
            allievo.setCognome(body.cognome());
            allievo.setEmail(body.email());
            allievo.setDataDiNascita(body.dataDiNascita());
            allievo.setDataScadenzaCertificato(body.dataScadenzaCertificato());
            allievo.setCodiceFiscale(body.codiceFiscale());
            allievo.setQuotaIscrizionePagata(body.quotaIscrizionePagata());
            allievo.setNoteSegreteria(body.noteSegreteria());
        }

        allievo.setNumeroScarpetta(body.numeroScarpetta());
        allievo.setMarcaScarpetta(body.marcaScarpetta());
        allievo.setHaPunte(body.haPunte());
        allievo.setMarcaPunte(body.marcaPunte());
        allievo.setLarghezzaPunte(body.larghezzaPunte());
        allievo.setTagliaBody(body.tagliaBody());
        allievo.setTagliaCalzini(body.tagliaCalzini());
        allievo.setAltezzaCm(body.altezzaCm());
        allievo.setTagliaPantalone(body.tagliaPantalone());
        allievo.setContattoEmergenzaNome(body.contattoEmergenzaNome());
        allievo.setContattoEmergenzaTelefono(body.contattoEmergenzaTelefono());
        allievo.setConsensoPrivacyFoto(body.consensoPrivacyFoto());

        return allievoRepository.save(allievo);
    }

    public Allievo disattiva(UUID id) {
        Allievo allievo = trovaPerId(id);
        allievo.setAccountAttivo(false);
        return allievoRepository.save(allievo);
    }

    public Allievo riattiva(UUID id) {
        Allievo allievo = trovaPerId(id);
        if (allievo.isMaiAttivato()) {
            throw new BadRequestException("Questo account non è mai stato attivato: non può essere riattivato. Usa \"Reinvia link\".");
        }
        allievo.setAccountAttivo(true);
        return allievoRepository.save(allievo);
    }

}
