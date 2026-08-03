package robertovisconti.be_capstone_msartballet.controllers.gallery;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Spettacolo;
import robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO.SpettacoloDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO.SpettacoloRespDTO;
import robertovisconti.be_capstone_msartballet.services.gallery.SpettacoloService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/spettacoli")
public class SpettacoloController {
    private final SpettacoloService spettacoloService;

    public SpettacoloController(SpettacoloService spettacoloService) {
        this.spettacoloService = spettacoloService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public SpettacoloRespDTO creaSpettacolo(@RequestBody @Valid SpettacoloDTO body) {
        return mappa(spettacoloService.creaSpettacolo(body));
    }

    @GetMapping
    public List<SpettacoloRespDTO> trovaTutti() {
        return spettacoloService.trovaTutti().stream().map(this::mappa).toList();
    }

    @GetMapping("/{id}")
    public SpettacoloRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(spettacoloService.trovaPerId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SpettacoloRespDTO modificaSpettacolo(@PathVariable UUID id, @RequestBody @Valid SpettacoloDTO body) {
        return mappa(spettacoloService.modificaSpettacolo(id, body));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaSpettacolo(@PathVariable UUID id) {
        spettacoloService.eliminaSpettacolo(id);
    }

    private SpettacoloRespDTO mappa(Spettacolo spettacolo) {
        return new SpettacoloRespDTO(spettacolo.getId(), spettacolo.getTitolo(), spettacolo.getDescrizione(), spettacolo.getDataEvento(), spettacolo.getLuogo());
    }
}
