package robertovisconti.be_capstone_msartballet.payloadsDTO.corsoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewDisciplinaDTO(

        @NotBlank(message = "Il nome è obbligatorio!")
        String nome,

        @Size(max = 1000, message = "La descrizione non può superare i 1000 caratteri.")
        String descrizione
) {
}
