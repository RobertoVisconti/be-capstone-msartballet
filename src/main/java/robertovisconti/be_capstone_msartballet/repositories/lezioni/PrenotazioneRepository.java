package robertovisconti.be_capstone_msartballet.repositories.lezioni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import robertovisconti.be_capstone_msartballet.entities.Prenotazione;

import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID>, JpaSpecificationExecutor<Prenotazione> {

    boolean existsByUtente_IdAndLezione_Id(UUID idUtente, UUID idLezione);

}
