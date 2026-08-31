package robertovisconti.be_capstone_msartballet.repositories.lezioni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import robertovisconti.be_capstone_msartballet.entities.Lezione;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface LezioneRepository extends JpaRepository<Lezione, UUID>, JpaSpecificationExecutor<Lezione> {
    List<Lezione> findBySala_IdAndDataOraInizioLessThanAndDataOraFineGreaterThan(
            UUID idSala, LocalDateTime dataOraFine, LocalDateTime dataOraInizio);
}
