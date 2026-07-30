package robertovisconti.be_capstone_msartballet.repositories.lezioni;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.Lezione;

import java.util.UUID;

public interface LezioneRepository extends JpaRepository<Lezione, UUID> {
}
