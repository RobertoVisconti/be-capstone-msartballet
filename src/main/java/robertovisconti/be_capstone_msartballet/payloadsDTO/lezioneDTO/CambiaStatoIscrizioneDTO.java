package robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO;

import jakarta.validation.constraints.NotNull;
import robertovisconti.be_capstone_msartballet.enums.StatoIscrizione;

public record CambiaStatoIscrizioneDTO(
        @NotNull(message = "Lo stato è obbligatorio!")
        StatoIscrizione stato
) {
}
