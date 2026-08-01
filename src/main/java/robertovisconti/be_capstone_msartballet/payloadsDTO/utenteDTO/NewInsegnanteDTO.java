package robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NewInsegnanteDTO(

        @NotBlank(message = "Il nome è obbligatorio!")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio!")
        String cognome,

        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "Formato email non valido")
        String email,

        @NotNull(message = "La data di nascita è obbligatoria!")
        @Past(message = "La data di nascità deve essere nel passato")
        LocalDate dataDiNascita,

        String imgProfilo,

        @NotBlank(message = "La biografia è obbligatoria!")
        @Size(max = 2000, message = "La biografica non può superare i 2000 caratteri!")
        String biografia

) {
}
