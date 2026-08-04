package robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record NewAdminDTO(
        @NotBlank(message = "Il nome è obbligatorio!")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio!")
        String cognome,

        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "Formato email non valido")
        String email,

        @NotNull(message = "La data di nascita è obbligatoria!")
        @Past(message = "La data di nascita deve essere nel passato")
        LocalDate dataDiNascita,

        String imgProfilo
) {
}
