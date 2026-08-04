package robertovisconti.be_capstone_msartballet.controllers.pagamenti;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Transazione;
import robertovisconti.be_capstone_msartballet.payloadsDTO.pagamentoDTO.NewTransazioneDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.pagamentoDTO.TransazioneRespDTO;
import robertovisconti.be_capstone_msartballet.services.pagamenti.TransazioneService;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/transazioni")
public class TransazioneController {

    private final TransazioneService transazioneService;

    public TransazioneController(TransazioneService transazioneService) {
        this.transazioneService = transazioneService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public TransazioneRespDTO creaTransazione(@RequestBody @Valid NewTransazioneDTO body) {
        return mappa(transazioneService.creaTransazione(body));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<TransazioneRespDTO> trovaConFiltri(
            @RequestParam(required = false) UUID idUtente,
            @RequestParam(required = false) UUID idProdotto,
            @RequestParam(required = false) UUID idCorso,
            @RequestParam(required = false) UUID idSala,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime al,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return transazioneService.trovaConFiltri(idUtente, idProdotto, idCorso, idSala, dal, al, pageable).map(this::mappa);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TransazioneRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(transazioneService.trovaPerId(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaTransazione(@PathVariable UUID id) {
        transazioneService.eliminaTransazione(id);
    }

    private TransazioneRespDTO mappa(Transazione transazione) {
        return new TransazioneRespDTO(
                transazione.getId(),
                transazione.getData_transazione(),
                transazione.getImporto(),
                transazione.getMetodoPagamento(),
                transazione.getUtente().getId(), transazione.getProdotto() != null ? transazione.getProdotto().getId() : null,
                transazione.getCorso() != null ? transazione.getCorso().getId() : null,
                transazione.getSala() != null ? transazione.getSala().getId() : null
        );
    }
}
