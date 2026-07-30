package robertovisconti.be_capstone_msartballet.repositories.corsi;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_capstone_msartballet.entities.Disciplina;

import java.util.UUID;

public interface DisciplinaRepository extends JpaRepository<Disciplina, UUID> {
}
