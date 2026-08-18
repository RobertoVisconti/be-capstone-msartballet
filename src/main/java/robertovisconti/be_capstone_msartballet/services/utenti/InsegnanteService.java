package robertovisconti.be_capstone_msartballet.services.utenti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Insegnante;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;
import robertovisconti.be_capstone_msartballet.exceptions.BadRequestException;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.AggiornaInsegnanteDTO;
import robertovisconti.be_capstone_msartballet.repositories.utenti.InsegnanteRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;

import java.util.UUID;

@Service
public class InsegnanteService {

    private final InsegnanteRepository insegnanteRepository;
    private final UtenteRepository utenteRepository;

    public InsegnanteService(InsegnanteRepository insegnanteRepository, UtenteRepository utenteRepository) {
        this.insegnanteRepository = insegnanteRepository;
        this.utenteRepository = utenteRepository;
    }

    public Page<Insegnante> trovaTutti(Pageable pageable) {
        return insegnanteRepository.findAll(pageable);
    }

    public Page<Insegnante> trovaAttivi(Pageable pageable) {
        return insegnanteRepository.findByAccountAttivoTrue(pageable);
    }


    public Insegnante trovaPerId(UUID id) {
        return insegnanteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun insegnante trovato con id " + id));
    }

    public Insegnante modificaInsegnante(UUID id, AggiornaInsegnanteDTO body, Utente richiedente) {
        Insegnante insegnante = trovaPerId(id);
        boolean isAdmin = richiedente.getRuolo() == RuoloUtente.ADMIN;

        if (!isAdmin && !insegnante.getId().equals(richiedente.getId())) {
            throw new AccessDeniedException("Non puoi modificare il profilo di un altro insegnante");
        }

        if (isAdmin) {
            if (!insegnante.getEmail().equals(body.email()) && utenteRepository.existsByEmail(body.email())) {
                throw new BadRequestException("L'email " + body.email() + " è già in uso");
            }
            insegnante.setNome(body.nome());
            insegnante.setCognome(body.cognome());
            insegnante.setEmail(body.email());
            insegnante.setDataDiNascita(body.dataDiNascita());
        }

        insegnante.setBiografia(body.biografia());

        return insegnanteRepository.save(insegnante);
    }

    public Insegnante disattiva(UUID id) {
        Insegnante insegnante = trovaPerId(id);
        insegnante.setAccountAttivo(false);
        return insegnanteRepository.save(insegnante);
    }

    public Insegnante riattiva(UUID id) {
        Insegnante insegnante = trovaPerId(id);
        insegnante.setAccountAttivo(true);
        return insegnanteRepository.save(insegnante);
    }

}
