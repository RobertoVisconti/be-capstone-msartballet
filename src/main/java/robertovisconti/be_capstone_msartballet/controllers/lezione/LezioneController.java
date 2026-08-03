package robertovisconti.be_capstone_msartballet.controllers.lezione;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Lezione;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.LezioneRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.NewLezioneDTO;
import robertovisconti.be_capstone_msartballet.services.lezione.LezioneService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/lezioni")
public class LezioneController {

    private final LezioneService lezioneService;

    public LezioneController(LezioneService lezioneService) {
        this.lezioneService = lezioneService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public LezioneRespDTO creaLezione(@RequestBody @Valid NewLezioneDTO body) {
        return mappa(lezioneService.creaLezione(body));
    }

    @GetMapping
    public Page<LezioneRespDTO> trovaConFiltri(
            @RequestParam(required = false) UUID idCorso,
            @RequestParam(required = false) UUID idSala,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime al,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return lezioneService.trovaConFiltri(idCorso, idSala, dal, al, pageable).map(this::mappa);
    }

    @GetMapping("/{id}")
    public LezioneRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(lezioneService.trovaPerId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public LezioneRespDTO modificaLezione(@PathVariable UUID id, @RequestBody @Valid NewLezioneDTO body) {
        return mappa(lezioneService.modificaLezione(id, body));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaLezione(@PathVariable UUID id) {
        lezioneService.eliminaLezione(id);
    }

    private LezioneRespDTO mappa(Lezione lezione) {
        return new LezioneRespDTO(
                lezione.getId(),
                lezione.getDataOraInizio(),
                lezione.getDataOraFine(),
                lezione.getPrezzoLezione(),
                lezione.getCorso().getId(),
                lezione.getCorso().getTitolo(),
                lezione.getSala().getId(),
                lezione.getSala().getTitolo()
        );
    }
}
