package robertovisconti.be_capstone_msartballet.payloadsDTO.salaDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record SalaDTO(

        @NotBlank(message = "Il titolo è obbligatorio")
        String titolo,

        @NotBlank(message = "L'immagine della sala è obbligatoria!")
        String imgSala,

        @NotNull(message = "Il prezzo è obbligatorio!")
        @PositiveOrZero(message = "Il prezzo di affitto non può essere negativo")
        Double prezzoAffitto
) {
}
