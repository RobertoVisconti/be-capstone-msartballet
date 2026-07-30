package robertovisconti.be_capstone_msartballet.repositories.pagamenti;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.Transazione;

import java.util.UUID;

public interface TransazioneRepository extends JpaRepository<Transazione, UUID> {
}
