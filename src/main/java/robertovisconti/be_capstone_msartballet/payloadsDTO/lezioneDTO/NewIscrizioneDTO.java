package robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewIscrizioneDTO(

        @NotNull(message = "L'allievo è obbligatorio!")
        UUID idAllievo,

        @NotNull(message = "Il corso è obbligatorio!")
        UUID idCorso
) {
}
