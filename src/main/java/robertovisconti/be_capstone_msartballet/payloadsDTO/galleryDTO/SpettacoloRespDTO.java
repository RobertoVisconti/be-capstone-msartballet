package robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO;

import java.time.LocalDate;
import java.util.UUID;

public record SpettacoloRespDTO(
        UUID id,
        String titolo,
        String descrizione,
        LocalDate dataEvento,
        String luogo
) {
}
