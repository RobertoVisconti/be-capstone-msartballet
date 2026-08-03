package robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordDTO(

        @NotBlank(message = "Il token è obbligatorio!")
        String token,

        @NotBlank(message = "La nuova password è obbligatoria!")
        @Size(min = 8, max = 100, message = "La password deve avere almeno 8 caratteri")
        String nuovaPassword
) {
}
