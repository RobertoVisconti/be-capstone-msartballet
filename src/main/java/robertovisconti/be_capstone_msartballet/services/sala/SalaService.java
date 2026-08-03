package robertovisconti.be_capstone_msartballet.services.sala;

import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Sala;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.salaDTO.SalaDTO;
import robertovisconti.be_capstone_msartballet.repositories.sale.SalaRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
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

    public void eliminaSala(UUID id) {
        salaRepository.delete(trovaPerId(id));
    }
}
