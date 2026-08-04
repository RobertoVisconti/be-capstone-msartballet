package robertovisconti.be_capstone_msartballet.controllers.utente;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.AggiornaInsegnanteDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.InsegnanteRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.UtenteMapper;
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
        return insegnanteService.trovaTutti(pageable).map(UtenteMapper::mappaInsegnante);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public InsegnanteRespDTO trovaPerId(@PathVariable UUID id) {
        return UtenteMapper.mappaInsegnante(insegnanteService.trovaPerId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','INSEGNANTE')")
    public InsegnanteRespDTO modificaInsegnante(@PathVariable UUID id, @RequestBody @Valid AggiornaInsegnanteDTO body, @AuthenticationPrincipal Utente richiedente) {
        return UtenteMapper.mappaInsegnante(insegnanteService.modificaInsegnante(id, body, richiedente));
    }

    @PatchMapping("/{id}/disattiva")
    @PreAuthorize("hasRole('ADMIN')")
    public InsegnanteRespDTO disattiva(@PathVariable UUID id) {
        return UtenteMapper.mappaInsegnante(insegnanteService.disattiva(id));
    }

    @PatchMapping("/{id}/riattiva")
    @PreAuthorize("hasRole('ADMIN')")
    public InsegnanteRespDTO riattiva(@PathVariable UUID id) {
        return UtenteMapper.mappaInsegnante(insegnanteService.riattiva(id));
    }

}
