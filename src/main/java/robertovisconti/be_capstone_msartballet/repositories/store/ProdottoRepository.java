package robertovisconti.be_capstone_msartballet.repositories.store;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.Prodotto;

import java.util.UUID;

public interface ProdottoRepository extends JpaRepository<Prodotto, UUID> {
}
