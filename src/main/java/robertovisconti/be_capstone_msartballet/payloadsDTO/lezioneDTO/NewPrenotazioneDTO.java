package robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewPrenotazioneDTO(

        @NotNull(message = "L'utente è obbligatorio!")
        UUID idUtente,

        @NotNull(message = "La lezione è obbligatoria!")
        UUID idLezione
) {
}
