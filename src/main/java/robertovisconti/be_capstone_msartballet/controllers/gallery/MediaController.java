package robertovisconti.be_capstone_msartballet.controllers.gallery;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.entities.Media;
import robertovisconti.be_capstone_msartballet.enums.TipoMedia;
import robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO.MediaRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO.NewMediaDTO;
import robertovisconti.be_capstone_msartballet.services.gallery.MediaService;
import robertovisconti.be_capstone_msartballet.tools.CloudinaryUploaderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.UUID;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;
    private final CloudinaryUploaderService cloudinaryUploaderService;


    public MediaController(MediaService mediaService, CloudinaryUploaderService cloudinaryUploaderService) {
        this.mediaService = mediaService;
        this.cloudinaryUploaderService = cloudinaryUploaderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public MediaRespDTO creaMedia(@RequestBody @Valid NewMediaDTO body) {
        return mappa(mediaService.creaMedia(body));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public MediaRespDTO caricaMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam TipoMedia tipoMedia,
            @RequestParam String titolo,
            @RequestParam UUID idSpettacolo
    ) {
        return mappa(mediaService.creaMediaConUpload(file, tipoMedia, titolo, idSpettacolo));
    }


    @GetMapping
    public Page<MediaRespDTO> trovaTutti(@PageableDefault(size = 20) Pageable pageable) {
        return mediaService.trovaTutti(pageable).map(this::mappa);
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
