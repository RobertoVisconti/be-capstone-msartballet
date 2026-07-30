package robertovisconti.be_capstone_msartballet.payloadsDTO.corsoDTO;

import java.util.UUID;

public record DisciplinaRespDTO(
        UUID id,
        String nome,
        String descrizione
) {
}
