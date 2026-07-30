package robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO;

import robertovisconti.be_capstone_msartballet.enums.StatoIscrizione;

import java.time.LocalDateTime;
import java.util.UUID;

public record IscrizioneRespDTO(
        UUID id,
        LocalDateTime dataIscrizione,
        StatoIscrizione stato,
        UUID idAllievo,
        String nomeAllievo,
        UUID idCorso,
        String titoloCorso
) {
}
