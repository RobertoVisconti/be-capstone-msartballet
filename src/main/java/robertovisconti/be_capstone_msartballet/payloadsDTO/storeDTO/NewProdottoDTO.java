package robertovisconti.be_capstone_msartballet.payloadsDTO.storeDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record NewProdottoDTO(

        @NotBlank(message = "Il titolo è obbligatorio!")
        String titolo,

        @Size(max = 1000, message = "La descrizione non può superare i 1000 caratteri")
        String descrizioneProdotto,

        String imgProdotto,

        @NotNull(message = "Il prezzo è obbligatorio!")
        @PositiveOrZero(message = "Il prezzo non può essere negativo")
        Double prezzoProdotto
) {
}
