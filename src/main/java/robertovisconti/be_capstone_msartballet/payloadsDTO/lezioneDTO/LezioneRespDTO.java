package robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record LezioneRespDTO(
        UUID id,
        LocalDateTime dataOraInizio,
        LocalDateTime dataOraFine,
        Double prezzoLezione,
        UUID idCorso,
        String titoloCorso,
        UUID idSala,
        String titoloSala
) {
}
