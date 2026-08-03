package robertovisconti.be_capstone_msartballet.repositories.utenti;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.TokenResetPassword;

import java.util.Optional;
import java.util.UUID;

public interface TokenResetPasswordRepository extends JpaRepository<TokenResetPassword, UUID> {

    Optional<TokenResetPassword> findByToken(String token);
}
