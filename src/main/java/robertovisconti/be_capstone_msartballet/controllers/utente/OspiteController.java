package robertovisconti.be_capstone_msartballet.controllers.utente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import robertovisconti.be_capstone_msartballet.entities.Ospite;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.OspiteRespDTO;
import robertovisconti.be_capstone_msartballet.services.utenti.OspiteService;

import java.util.UUID;

@RestController
@RequestMapping("/utenti/ospiti")
public class OspiteController {

    private final OspiteService ospiteService;

    public OspiteController(OspiteService ospiteService) {
        this.ospiteService = ospiteService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<OspiteRespDTO> trovaTutti(@PageableDefault(size = 20) Pageable pageable) {
        return ospiteService.trovaTutti(pageable).map(this::mappa);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OspiteRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(ospiteService.trovaPerId(id));
    }

    private OspiteRespDTO mappa(Ospite ospite) {
        return new OspiteRespDTO(
                ospite.getId(),
                ospite.getNome(),
                ospite.getCognome(),
                ospite.getEmail(),
                ospite.getDataDiNascita(),
                ospite.getImgProfilo(),
                ospite.getRuolo(),
                ospite.getDataRegistrazione()
        );
    }
}
