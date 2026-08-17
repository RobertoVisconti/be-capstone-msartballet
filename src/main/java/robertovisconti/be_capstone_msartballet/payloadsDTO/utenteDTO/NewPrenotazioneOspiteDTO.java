package robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewPrenotazioneOspiteDTO(

        @NotBlank(message = "Il nome è obbligatorio!")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio!")
        String cognome,

        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "Email non valida")
        String email,

        String telefono,

        @NotNull(message = "La lezione è obbligatoria!")
        UUID idLezione
) {
}
