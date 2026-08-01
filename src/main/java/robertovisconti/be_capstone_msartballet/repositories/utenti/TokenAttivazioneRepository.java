package robertovisconti.be_capstone_msartballet.repositories.utenti;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.TokenAttivazione;

import java.util.Optional;
import java.util.UUID;

public interface TokenAttivazioneRepository extends JpaRepository<TokenAttivazione, UUID> {

    Optional<TokenAttivazione> findByToken(String token);
}
