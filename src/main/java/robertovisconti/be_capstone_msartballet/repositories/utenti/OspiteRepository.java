package robertovisconti.be_capstone_msartballet.repositories.utenti;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.Ospite;

import java.util.UUID;

public interface OspiteRepository extends JpaRepository<Ospite, UUID> {
}
