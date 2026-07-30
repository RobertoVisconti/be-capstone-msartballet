package robertovisconti.be_capstone_msartballet.repositories.corsi;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.Corso;

import java.util.UUID;

public interface CorsoRepository extends JpaRepository<Corso, UUID> {
}
