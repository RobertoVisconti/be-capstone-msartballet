package robertovisconti.be_capstone_msartballet.controllers.corso;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Corso;
import robertovisconti.be_capstone_msartballet.enums.GiornoSettimana;
import robertovisconti.be_capstone_msartballet.enums.LivelloCorso;
import robertovisconti.be_capstone_msartballet.payloadsDTO.corsoDTO.CorsoRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.corsoDTO.NewCorsoDTO;
import robertovisconti.be_capstone_msartballet.services.corso.CorsoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/corsi")
public class CorsoController {
    private final CorsoService corsoService;

    public CorsoController(CorsoService corsoService) {
        this.corsoService = corsoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CorsoRespDTO creaCorso(@RequestBody @Valid NewCorsoDTO body) {
        return mappa(corsoService.creaCorso(body));
    }

    @GetMapping
    public List<CorsoRespDTO> trovaConFiltri(
            @RequestParam(required = false) UUID idDisciplina,
            @RequestParam(required = false) LivelloCorso livelloCorso,
            @RequestParam(required = false) GiornoSettimana giornoSettimana,
            @RequestParam(required = false) Double prezzoMin,
            @RequestParam(required = false) Double prezzoMax
    ) {
        return corsoService.trovaConFiltri(idDisciplina, livelloCorso, giornoSettimana, prezzoMin, prezzoMax)
                .stream().map(this::mappa).toList();
    }

    @GetMapping("/{id}")
    public CorsoRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(corsoService.trovaPerId(id));
    }

    @PutMapping("/{id}")
    public CorsoRespDTO modificaCorso(@PathVariable UUID id, @RequestBody @Valid NewCorsoDTO body) {
        return mappa(corsoService.modificaCorso(id, body));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaCorso(@PathVariable UUID id) {
        corsoService.eliminaCorso(id);
    }

    private CorsoRespDTO mappa(Corso corso) {
        return new CorsoRespDTO(
                corso.getId(),
                corso.getTitolo(),
                corso.getDescrizione(),
                corso.getLivelloCorso(),
                corso.getGiornoSettimana(),
                corso.getOraInizio(),
                corso.getOra_fine(),
                corso.getPrezzoMensile(),
                corso.getDisciplina().getId(),
                corso.getDisciplina().getNome()
        );
    }
}
