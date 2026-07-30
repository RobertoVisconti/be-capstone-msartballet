package robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO;

import robertovisconti.be_capstone_msartballet.enums.StatoPrenotazione;

import java.time.LocalDateTime;
import java.util.UUID;

public record PrenotazioneRespDTO(
        UUID id,
        StatoPrenotazione statoPrenotazione,
        LocalDateTime dataPrenotazione,
        UUID idUtente,
        String nomeUtente,
        UUID idLezione
) {
}
