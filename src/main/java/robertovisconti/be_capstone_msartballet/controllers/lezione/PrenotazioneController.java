package robertovisconti.be_capstone_msartballet.controllers.lezione;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Prenotazione;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.enums.StatoPrenotazione;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.CambiaStatoPrenotazioneDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.NewPrenotazioneDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.PrenotazioneRespDTO;
import robertovisconti.be_capstone_msartballet.services.lezione.PrenotazioneService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    private PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ALLIEVO','OSPITE','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public PrenotazioneRespDTO creaPrenotazione(@RequestBody @Valid NewPrenotazioneDTO body, @AuthenticationPrincipal Utente richiedente) {
        return mappa(prenotazioneService.creaPrenotazione(body, richiedente));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<PrenotazioneRespDTO> trovaConFiltri(
            @RequestParam(required = false) UUID idUtente,
            @RequestParam(required = false) UUID idLezione,
            @RequestParam(required = false) StatoPrenotazione stato,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return prenotazioneService.trovaConFiltri(idUtente, idLezione, stato, pageable).map(this::mappa);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PrenotazioneRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(prenotazioneService.trovaPerId(id));
    }

    @PatchMapping("/{id}/stato")
    @PreAuthorize("hasRole('ADMIN')")
    public PrenotazioneRespDTO cambiaStato(@PathVariable UUID id, @RequestBody @Valid CambiaStatoPrenotazioneDTO body) {
        return mappa(prenotazioneService.cambiaStato(id, body));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaPrenotazione(@PathVariable UUID id) {
        prenotazioneService.eliminaPrenotazione(id);
    }

    private PrenotazioneRespDTO mappa(Prenotazione prenotazione) {
        return new PrenotazioneRespDTO(
                prenotazione.getId(),
                prenotazione.getStatoPrenotazione(),
                prenotazione.getDataPrenotazione(),
                prenotazione.getUtente().getId(),
                prenotazione.getUtente().getNome() + " " + prenotazione.getUtente().getCognome(),
                prenotazione.getLezione().getId()
        );
    }
}
