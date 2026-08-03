package robertovisconti.be_capstone_msartballet.controllers.lezione;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Lezione;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.LezioneRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO.NewLezioneDTO;
import robertovisconti.be_capstone_msartballet.services.lezione.LezioneService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lezioni")
public class LezioneController {
    private LezioneService lezioneService;

    public LezioneController(LezioneService lezioneService) {
        this.lezioneService = lezioneService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LezioneRespDTO creaLezione(@RequestBody @Valid NewLezioneDTO body) {
        return mappa(lezioneService.creaLezione(body));
    }

    @GetMapping
    public List<LezioneRespDTO> trovaConFiltri(
            @RequestParam(required = false) UUID idCorso,
            @RequestParam(required = false) UUID idSala,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime al
    ) {
        return lezioneService.trovaConFiltri(idCorso, idSala, dal, al).stream().map(this::mappa).toList();
    }

    @GetMapping("/{id}")
    public LezioneRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(lezioneService.trovaPerId(id));
    }

    @PutMapping("/{id}")
    public LezioneRespDTO modificaLezione(@PathVariable UUID id, @RequestBody @Valid NewLezioneDTO body) {
        return mappa(lezioneService.modificaLezione(id, body));
    }

    @DeleteMapping("/{id}")
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
