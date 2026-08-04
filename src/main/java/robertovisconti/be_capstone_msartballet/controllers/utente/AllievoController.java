package robertovisconti.be_capstone_msartballet.controllers.utente;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.AggiornaAllievoDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.AllievoRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.UtenteMapper;
import robertovisconti.be_capstone_msartballet.services.utenti.AllievoService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/utenti/allievi")
public class AllievoController {

    private final AllievoService allievoService;

    public AllievoController(AllievoService allievoService) {
        this.allievoService = allievoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AllievoRespDTO> trovaConFiltri(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) Boolean accountAttivo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate certificatoScadeEntro,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return allievoService.trovaConFiltri(nome, cognome, accountAttivo, certificatoScadeEntro, pageable).map(UtenteMapper::mappaAllievo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AllievoRespDTO trovaPerId(@PathVariable UUID id) {
        return UtenteMapper.mappaAllievo(allievoService.trovaPerId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ALLIEVO')")
    public AllievoRespDTO modificaAllievo(@PathVariable UUID id, @RequestBody @Valid AggiornaAllievoDTO body, @AuthenticationPrincipal Utente richiedente) {
        return UtenteMapper.mappaAllievo(allievoService.modificaAllievo(id, body, richiedente));
    }

}
