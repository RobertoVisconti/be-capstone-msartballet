package robertovisconti.be_capstone_msartballet.payloadsDTO;

import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;

import java.util.UUID;

public record LoginRespDTO(
        String accessToken,
        UUID id,
        String nome,
        String cognome,
        RuoloUtente ruolo
) {
}
