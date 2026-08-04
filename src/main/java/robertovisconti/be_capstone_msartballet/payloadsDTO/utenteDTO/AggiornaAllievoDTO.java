package robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO;

import jakarta.validation.constraints.*;
import robertovisconti.be_capstone_msartballet.enums.LarghezzaPunte;

import java.time.LocalDate;

public record AggiornaAllievoDTO(
        @NotBlank(message = "Il nome è obbligatorio!")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        String cognome,

        @NotBlank(message = "L'email è obbligatoria!")
        @Email(message = "Formato email non valido")
        String email,

        @NotNull(message = "La data di nascita è obbligatoria")
        LocalDate dataDiNascita,

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

        @Future(message = "La data di scadenza del certificato deve essere futura!")
        LocalDate dataScadenzaCertificato,

        String contattoEmergenzaNome,

        @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Numero di telefono non valido")
        String contattoEmergenzaTelefono,

        @Pattern(
                regexp = "^[A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]$",
                message = "Formato codice fiscale non valido"
        )
        String codiceFiscale,

        Boolean quotaIscrizionePagata,

        Boolean consensoPrivacyFoto,

        @Size(max = 2000, message = "Le note non possono superare i 2000 caratteri")
        String noteSegreteria
) {
}
