package robertovisconti.be_capstone_msartballet.controllers.gallery;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Media;
import robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO.MediaRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO.NewMediaDTO;
import robertovisconti.be_capstone_msartballet.services.gallery.MediaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/media")
public class MediaController {
    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public MediaRespDTO creaMedia(@RequestBody @Valid NewMediaDTO body) {
        return mappa(mediaService.creaMedia(body));
    }

    @GetMapping
    public List<MediaRespDTO> trovaTutti() {
        return mediaService.trovaTutti().stream().map(this::mappa).toList();
    }

    @GetMapping("/{id}")
    public MediaRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(mediaService.trovaPerId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MediaRespDTO modificaMedia(@PathVariable UUID id, @RequestBody @Valid NewMediaDTO body) {
        return mappa(mediaService.modificaMedia(id, body));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaMedia(@PathVariable UUID id) {
        mediaService.eliminaMedia(id);
    }

    private MediaRespDTO mappa(Media media) {
        return new MediaRespDTO(media.getId(), media.getUrl(), media.getTipoMedia(), media.getTitolo(), media.getSpettacolo().getId());
    }
}
