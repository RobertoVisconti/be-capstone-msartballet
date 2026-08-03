package robertovisconti.be_capstone_msartballet.controllers.sala;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.entities.Sala;
import robertovisconti.be_capstone_msartballet.payloadsDTO.salaDTO.SalaDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.salaDTO.SalaRespDTO;
import robertovisconti.be_capstone_msartballet.services.sala.SalaService;
import robertovisconti.be_capstone_msartballet.tools.CloudinaryUploaderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sale")
public class SalaController {
    private final SalaService salaService;
    private final CloudinaryUploaderService cloudinaryUploaderService;

    public SalaController(SalaService salaService, CloudinaryUploaderService cloudinaryUploaderService) {
        this.salaService = salaService;
        this.cloudinaryUploaderService = cloudinaryUploaderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public SalaRespDTO modificaSala(@PathVariable UUID id, @RequestBody @Valid SalaDTO body) {
        return mappa(salaService.modificaSala(id, body));
    }

    @PostMapping(value = "/{id}/immagine", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public SalaRespDTO caricaImmagine(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        return mappa(salaService.caricaImmagine(id, file));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaSala(@PathVariable UUID id) {
        salaService.eliminaSala(id);
    }

    private SalaRespDTO mappa(Sala sala) {
        return new SalaRespDTO(sala.getId(), sala.getTitolo(), sala.getImgSala(), sala.getPrezzoAffitto());
    }
}
