package robertovisconti.be_capstone_msartballet.repositories.gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.Spettacolo;

import java.util.UUID;

public interface SpettacoloRepository extends JpaRepository<Spettacolo, UUID> {
}
