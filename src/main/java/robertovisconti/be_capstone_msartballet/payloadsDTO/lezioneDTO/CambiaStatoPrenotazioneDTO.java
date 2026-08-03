package robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO;

import jakarta.validation.constraints.NotNull;
import robertovisconti.be_capstone_msartballet.enums.StatoPrenotazione;

public record CambiaStatoPrenotazioneDTO(
        @NotNull(message = "Lo stato è obbligatorio!")
        StatoPrenotazione statoPrenotazione
) {
}
