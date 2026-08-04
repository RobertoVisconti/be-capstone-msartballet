package robertovisconti.be_capstone_msartballet.payloadsDTO.pagamentoDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransazioneRespDTO(
        UUID id,
        LocalDateTime dataTransazione,
        Double importo,
        String metodoPagamento,
        UUID idUtente,
        UUID idProdotto,
        UUID idCorso,
        UUID idSala
) {
}
