package robertovisconti.be_capstone_msartballet.services.sala;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.entities.Sala;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.salaDTO.SalaDTO;
import robertovisconti.be_capstone_msartballet.repositories.sale.SalaRepository;
import robertovisconti.be_capstone_msartballet.tools.CloudinaryUploaderService;

import java.util.List;
import java.util.UUID;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final CloudinaryUploaderService cloudinaryUploaderService;

    public SalaService(SalaRepository salaRepository, CloudinaryUploaderService cloudinaryUploaderService) {
        this.salaRepository = salaRepository;
        this.cloudinaryUploaderService = cloudinaryUploaderService;
    }

    public Sala creaSala(SalaDTO body) {
        Sala nuovaSala = new Sala(body.titolo(), body.imgSala(), body.prezzoAffitto());
        return salaRepository.save(nuovaSala);
    }

    public List<Sala> trovaTutte() {
        return salaRepository.findAll();
    }

    public Sala trovaPerId(UUID id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessuna sala trovata con id " + id));
    }

    public Sala modificaSala(UUID id, SalaDTO body) {
        Sala sala = trovaPerId(id);
        sala.setTitolo(body.titolo());
        sala.setImgSala(body.imgSala());
        sala.setPrezzoAffitto(body.prezzoAffitto());
        return salaRepository.save(sala);
    }

    public Sala caricaImmagine(UUID id, MultipartFile file) {
        Sala sala = trovaPerId(id);
        String url = cloudinaryUploaderService.caricaImmagine(file);
        sala.setImgSala(url);
        return salaRepository.save(sala);
    }


    public void eliminaSala(UUID id) {
        salaRepository.delete(trovaPerId(id));
    }
}
