package robertovisconti.be_capstone_msartballet.repositories.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.Sala;

import java.util.UUID;

public interface SalaRepository extends JpaRepository<Sala, UUID> {
}
