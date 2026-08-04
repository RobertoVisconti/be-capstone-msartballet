package robertovisconti.be_capstone_msartballet.controllers.utente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import robertovisconti.be_capstone_msartballet.entities.Insegnante;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.InsegnanteRespDTO;
import robertovisconti.be_capstone_msartballet.services.utenti.InsegnanteService;

import java.util.UUID;

@RestController
@RequestMapping("/utenti/insegnanti")
public class InsegnanteController {

    private final InsegnanteService insegnanteService;

    public InsegnanteController(InsegnanteService insegnanteService) {
        this.insegnanteService = insegnanteService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<InsegnanteRespDTO> trovaTutti(@PageableDefault(size = 20) Pageable pageable) {
        return insegnanteService.trovaTutti(pageable).map(this::mappa);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public InsegnanteRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(insegnanteService.trovaPerId(id));
    }

    private InsegnanteRespDTO mappa(Insegnante insegnante) {
        return new InsegnanteRespDTO(
                insegnante.getId(),
                insegnante.getNome(),
                insegnante.getCognome(),
                insegnante.getEmail(),
                insegnante.getDataDiNascita(),
                insegnante.getImgProfilo(),
                insegnante.getRuolo(),
                insegnante.getDataRegistrazione(),
                insegnante.getAccountAttivo(),
                insegnante.getBiografia()
        );
    }
}
