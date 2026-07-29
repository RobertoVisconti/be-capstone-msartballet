package robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO;

import jakarta.validation.constraints.*;
import robertovisconti.be_capstone_msartballet.enums.LarghezzaPunte;

import java.time.LocalDate;

public record NewAllievoDTO(

        @NotBlank(message = "Il nome è obbligatorio!")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        String cognome,

        @NotBlank(message = "L'email è pbbligatoria!")
        @Email(message = "Formato email non valido")
        String email,

        @NotBlank(message = "La password è obbligatoria!")
        @Size(min = 8, max = 100, message = "La password deve avere almeno 8 caratteri")
        String password,

        @NotNull(message = "La data di nascità è obbligatoria")
        LocalDate dataDiNascita,

        String imgProfilo,

        String numeroScarpetta,

        String marcaScarpetta,

        Boolean haPunte,

        String marcaPunte,

        LarghezzaPunte larghezzaPunte,

        String tagliaBody,

        String tagliaCalzini,

        @Positive(message = "L'altezza deve essere un valore positivo!")
        @Max(value = 250, message = "Altezza non plausibile!")
        Integer altezzaCm,

        String tagliaPantalone,

        @Future(message = "La data di scadenza del certificato deve essere futura! ")
        LocalDate dataScadenzaCertificato,

        @NotBlank(message = "Il nome del contatto di emergenza è obbligatoria!")
        String contattoEmergenzaNome,

        @NotBlank(message = "Il telefono del contatto di emergenza è obbligatorio!")
        @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Numero di telefono non valido")
        String contattoEmergenzaTelefono,

        @NotBlank(message = "Il codice fiscale è obbligatorio")
        @Pattern(
                regexp = "^[A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]$",
                message = "formato codice fiscale non valido"
        )
        String codiceFiscale,

        @NotNull(message = "Obbligatorio indicare il consenso privacy per l'utilizzo delle foto")
        Boolean consensoPrivacyFoto


) {
}
