package robertovisconti.be_capstone_msartballet.services.gallery;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.entities.Media;
import robertovisconti.be_capstone_msartballet.entities.Spettacolo;
import robertovisconti.be_capstone_msartballet.enums.TipoMedia;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO.NewMediaDTO;
import robertovisconti.be_capstone_msartballet.repositories.gallery.MediaRepository;
import robertovisconti.be_capstone_msartballet.repositories.gallery.SpettacoloRepository;
import robertovisconti.be_capstone_msartballet.tools.CloudinaryUploaderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final SpettacoloRepository spettacoloRepository;
    private final CloudinaryUploaderService cloudinaryUploaderService;

    public MediaService(MediaRepository mediaRepository, SpettacoloRepository spettacoloRepository, CloudinaryUploaderService cloudinaryUploaderService) {
        this.mediaRepository = mediaRepository;
        this.spettacoloRepository = spettacoloRepository;
        this.cloudinaryUploaderService = cloudinaryUploaderService;
    }

    public Media creaMedia(NewMediaDTO body) {
        Spettacolo spettacolo = trovaSpettacolo(body.idSpettacolo());
        Media nuovoMedia = new Media(body.url(), body.tipoMedia(), body.titolo());
        nuovoMedia.setSpettacolo(spettacolo);
        return mediaRepository.save(nuovoMedia);
    }

    public Media creaMediaConUpload(MultipartFile file, TipoMedia tipoMedia, String titolo, UUID idSpettacolo) {
        Spettacolo spettacolo = trovaSpettacolo(idSpettacolo);
        String url = cloudinaryUploaderService.caricaMedia(file);
        Media nuovoMedia = new Media(url, tipoMedia, titolo);
        nuovoMedia.setSpettacolo(spettacolo);
        return mediaRepository.save(nuovoMedia);
    }

    public Page<Media> trovaTutti(Pageable pageable) {
        return mediaRepository.findAll(pageable);
    }

    public Media trovaPerId(UUID id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun media trovato con id " + id));
    }

    public Media modificaMedia(UUID id, NewMediaDTO body) {
        Media media = trovaPerId(id);
        media.setUrl(body.url());
        media.setTipoMedia(body.tipoMedia());
        media.setTitolo(body.titolo());
        media.setSpettacolo(trovaSpettacolo(body.idSpettacolo()));
        return mediaRepository.save(media);
    }

    public void eliminaMedia(UUID id) {
        mediaRepository.delete(trovaPerId(id));
    }

    private Spettacolo trovaSpettacolo(UUID idSpettacolo) {
        return spettacoloRepository.findById(idSpettacolo)
                .orElseThrow(() -> new NotFoundException("Nessuno spettacolo trovato con id " + idSpettacolo));
    }
}
