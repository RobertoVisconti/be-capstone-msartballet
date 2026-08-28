package robertovisconti.be_capstone_msartballet.services.utenti;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Ospite;
import robertovisconti.be_capstone_msartballet.exceptions.BadRequestException;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.repositories.utenti.OspiteRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;

import java.util.UUID;

@Service
public class OspiteService {

    private final OspiteRepository ospiteRepository;
    private final UtenteRepository utenteRepository;

    public OspiteService(OspiteRepository ospiteRepository, UtenteRepository utenteRepository) {
        this.ospiteRepository = ospiteRepository;
        this.utenteRepository = utenteRepository;
    }

    public Page<Ospite> trovaTutti(Pageable pageable) {
        return ospiteRepository.findAll(pageable);
    }

    public Ospite trovaPerId(UUID id) {
        return ospiteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun ospite trovato con id " + id));
    }

    public Ospite trovaOCrea(String nome, String cognome, String email, String telefono) {
        return utenteRepository.findByEmail(email)
                .map(utente -> {
                    if (!(utente instanceof Ospite ospite)) {
                        throw new BadRequestException("Questa email è già registrata con un altro ruolo: effettua il login per continuare.");
                    }
                    if (telefono != null && !telefono.isBlank()) {
                        ospite.setTelefono(telefono);
                    }
                    return ospiteRepository.save(ospite);
                })
                .orElseGet(() -> {
                    Ospite nuovoOspite = new Ospite(nome, cognome, email, null);
                    nuovoOspite.setTelefono(telefono);
                    return ospiteRepository.save(nuovoOspite);
                });
    }
}
