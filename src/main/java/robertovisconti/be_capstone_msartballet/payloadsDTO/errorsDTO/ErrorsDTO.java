package robertovisconti.be_capstone_msartballet.payloadsDTO.errorsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorsDTO(String message, LocalDateTime timestamp, List<String> listaErrori) {
    public ErrorsDTO(String message, LocalDateTime timestamp) {
        this(message, timestamp, null);
    }
}
