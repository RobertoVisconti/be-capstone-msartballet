package robertovisconti.be_capstone_msartballet.repositories.utenti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.Insegnante;

import java.util.UUID;

public interface InsegnanteRepository extends JpaRepository<Insegnante, UUID> {

    Page<Insegnante> findByAccountAttivoTrue(Pageable pageable);
}
