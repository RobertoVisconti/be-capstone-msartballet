package robertovisconti.be_capstone_msartballet.controllers.lezione;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Iscrizione;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.enums.StatoIscrizione;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.CambiaStatoIscrizioneDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.IscrizioneRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.NewIscrizioneDTO;
import robertovisconti.be_capstone_msartballet.services.lezione.IscrizioneService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/iscrizioni")
public class IscrizioneController {
    private final IscrizioneService iscrizioneService;

    public IscrizioneController(IscrizioneService iscrizioneService) {
        this.iscrizioneService = iscrizioneService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ALLIEVO')")
    @ResponseStatus(HttpStatus.CREATED)
    public IscrizioneRespDTO creaIscrizione(@RequestBody @Valid NewIscrizioneDTO body, @AuthenticationPrincipal Utente richiedente) {
        return mappa(iscrizioneService.creaIscrizione(body, richiedente));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<IscrizioneRespDTO> trovaConFiltri(
            @RequestParam(required = false) UUID idAllievo,
            @RequestParam(required = false) UUID idCorso,
            @RequestParam(required = false) StatoIscrizione stato
    ) {
        return iscrizioneService.trovaConFiltri(idAllievo, idCorso, stato).stream().map(this::mappa).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public IscrizioneRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(iscrizioneService.trovaPerId(id));
    }

    @PatchMapping("/{id}/stato")
    @PreAuthorize("hasRole('ADMIN')")
    public IscrizioneRespDTO cambiaStato(@PathVariable UUID id, @RequestBody @Valid CambiaStatoIscrizioneDTO body) {
        return mappa(iscrizioneService.cambiaStato(id, body));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaIscrizione(@PathVariable UUID id) {
        iscrizioneService.eliminaIscrizione(id);
    }

    private IscrizioneRespDTO mappa(Iscrizione iscrizione) {
        return new IscrizioneRespDTO(
                iscrizione.getId(),
                iscrizione.getDataIscrizione(),
                iscrizione.getStato(),
                iscrizione.getAllievo().getId(),
                iscrizione.getAllievo().getNome() + " " + iscrizione.getAllievo().getCognome(),
                iscrizione.getCorso().getId(),
                iscrizione.getCorso().getTitolo()
        );
    }
}
