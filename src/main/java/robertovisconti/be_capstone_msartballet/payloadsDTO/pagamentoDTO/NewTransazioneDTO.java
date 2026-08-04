package robertovisconti.be_capstone_msartballet.payloadsDTO.pagamentoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import robertovisconti.be_capstone_msartballet.validations.AcquistoValido;

import java.util.UUID;


@AcquistoValido
public record NewTransazioneDTO(

        @NotBlank(message = "Il metodo di pagamento è obbligatorio!")
        String metodoPagamento,

        @NotNull(message = "L'utente è obbligatorio!")
        UUID idUtente,
        
        UUID idProdotto,

        UUID idCorso,

        UUID idSala
) {
}
