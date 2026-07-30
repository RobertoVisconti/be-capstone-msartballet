package robertovisconti.be_capstone_msartballet.repositories.gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.Media;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
}
