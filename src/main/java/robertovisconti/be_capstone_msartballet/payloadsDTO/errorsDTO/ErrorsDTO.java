package robertovisconti.be_capstone_msartballet.payloadsDTO.errorsDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsDTO(String message, LocalDateTime timestamp, List<String> listaErrori) {
    public ErrorsDTO(String message, LocalDateTime timestamp) {
        this(message, timestamp, null);
    }
}
