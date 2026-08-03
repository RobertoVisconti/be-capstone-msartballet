package robertovisconti.be_capstone_msartballet.repositories.lezioni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import robertovisconti.be_capstone_msartballet.entities.Iscrizione;

import java.util.UUID;

public interface IscrizioneRepository extends JpaRepository<Iscrizione, UUID>, JpaSpecificationExecutor<Iscrizione> {
}
