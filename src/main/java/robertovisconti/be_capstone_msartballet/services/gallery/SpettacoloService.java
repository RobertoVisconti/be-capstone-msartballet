package robertovisconti.be_capstone_msartballet.services.gallery;

import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Spettacolo;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO.SpettacoloDTO;
import robertovisconti.be_capstone_msartballet.repositories.gallery.SpettacoloRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SpettacoloService {

    private SpettacoloRepository spettacoloRepository;

    public SpettacoloService(SpettacoloRepository spettacoloRepository) {
        this.spettacoloRepository = spettacoloRepository;
    }

    public Spettacolo creaSpettacolo(SpettacoloDTO body) {
        Spettacolo nuovoSpettacolo = new Spettacolo(body.titolo(), body.descrizione(), body.dataEvento(), body.luogo());
        return spettacoloRepository.save(nuovoSpettacolo);
    }

    public List<Spettacolo> trovaTutti() {
        return spettacoloRepository.findAll();
    }

    public Spettacolo trovaPerId(UUID id) {
        return spettacoloRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessuno spettacolo trovato con id " + id));
    }

    public Spettacolo modificaSpettacolo(UUID id, SpettacoloDTO body) {
        Spettacolo spettacolo = trovaPerId(id);
        spettacolo.setTitolo(body.titolo());
        spettacolo.setDescrizione(body.descrizione());
        spettacolo.setDataEvento(body.dataEvento());
        spettacolo.setLuogo(body.luogo());
        return spettacoloRepository.save(spettacolo);
    }

    public void eliminaSpettacolo(UUID id) {
        spettacoloRepository.delete(trovaPerId(id));
    }
}
