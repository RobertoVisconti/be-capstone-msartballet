package robertovisconti.be_capstone_msartballet.controllers.pagamenti;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Transazione;
import robertovisconti.be_capstone_msartballet.payloadsDTO.pagamentoDTO.NewTransazioneDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.pagamentoDTO.TransazioneRespDTO;
import robertovisconti.be_capstone_msartballet.services.pagamenti.TransazioneService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transazioni")
public class TransazioneController {
    private TransazioneService transazioneService;

    public TransazioneController(TransazioneService transazioneService) {
        this.transazioneService = transazioneService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransazioneRespDTO creaTransazione(@RequestBody @Valid NewTransazioneDTO body) {
        return mappa(transazioneService.creaTransazione(body));
    }

    @GetMapping
    public List<TransazioneRespDTO> trovaConFiltri(
            @RequestParam(required = false) UUID idUtente,
            @RequestParam(required = false) UUID idProdotto,
            @RequestParam(required = false) UUID idCorso,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime al
    ) {
        return transazioneService.trovaConFiltri(idUtente, idProdotto, idCorso, dal, al).stream().map(this::mappa).toList();
    }

    @GetMapping("/{id}")
    public TransazioneRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(transazioneService.trovaPerId(id));
    }

    @DeleteMapping("/{id}")
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
                transazione.getUtente().getId(),
                transazione.getProdotto().getId(),
                transazione.getCorso() != null ? transazione.getCorso().getId() : null
        );
    }
}
