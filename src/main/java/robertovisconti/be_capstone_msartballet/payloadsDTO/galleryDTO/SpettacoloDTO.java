package robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record SpettacoloDTO(

        @NotBlank(message = "Il titolo è obbligatorio!")
        String titolo,

        @Size(max = 2000, message = "La descrizione non può superare i 2000 caratteri")
        String descrizione,

        @NotNull(message = "La data dell'evento è obbligatoria!")
        @Past(message = "La data dell'evento non può essere nel futuro")
        LocalDate dataEvento,

        @NotBlank(message = "Il luogo è obbligatorio!")
        String luogo
) {
}
