package robertovisconti.be_capstone_msartballet.repositories.utenti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import robertovisconti.be_capstone_msartballet.entities.Allievo;

import java.util.UUID;

public interface AllievoRepository extends JpaRepository<Allievo, UUID>, JpaSpecificationExecutor<Allievo> {
}
