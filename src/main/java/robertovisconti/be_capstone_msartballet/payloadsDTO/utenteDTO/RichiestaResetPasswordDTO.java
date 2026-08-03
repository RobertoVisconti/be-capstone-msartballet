package robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RichiestaResetPasswordDTO(

        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "Formato email non valido")
        String email


) {
}
