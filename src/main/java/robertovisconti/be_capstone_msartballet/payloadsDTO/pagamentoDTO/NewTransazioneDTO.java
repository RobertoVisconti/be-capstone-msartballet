package robertovisconti.be_capstone_msartballet.payloadsDTO.pagamentoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record NewTransazioneDTO(

        @NotNull(message = "L'importo è obbligatorio!")
        @Positive(message = "L'importo deve essere maggiore di zero")
        Double importo,

        @NotBlank(message = "Il metodo di pagamento è obbligatorio!")
        String metodoPagamento,

        @NotNull(message = "L'utente è obbligatorio!")
        UUID idUtente,

        @NotNull(message = "Il prodotto è obbligatorio!")
        UUID idProdotto,

        // opzionale solo se riguarda la transazione di pagamento ad un corso
        UUID idCorso
) {
}
