package robertovisconti.be_capstone_msartballet.controllers.sala;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Sala;
import robertovisconti.be_capstone_msartballet.payloadsDTO.salaDTO.SalaDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.salaDTO.SalaRespDTO;
import robertovisconti.be_capstone_msartballet.services.sala.SalaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sale")
public class SalaController {
    private SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalaRespDTO creaSala(@RequestBody @Valid SalaDTO body) {
        return mappa(salaService.creaSala(body));
    }

    @GetMapping
    public List<SalaRespDTO> trovaTutte() {
        return salaService.trovaTutte().stream().map(this::mappa).toList();
    }

    @GetMapping("/{id}")
    public SalaRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(salaService.trovaPerId(id));
    }

    @PutMapping("/{id}")
    public SalaRespDTO modificaSala(@PathVariable UUID id, @RequestBody @Valid SalaDTO body) {
        return mappa(salaService.modificaSala(id, body));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaSala(@PathVariable UUID id) {
        salaService.eliminaSala(id);
    }

    private SalaRespDTO mappa(Sala sala) {
        return new SalaRespDTO(sala.getId(), sala.getTitolo(), sala.getImgSala(), sala.getPrezzoAffitto());
    }
}
