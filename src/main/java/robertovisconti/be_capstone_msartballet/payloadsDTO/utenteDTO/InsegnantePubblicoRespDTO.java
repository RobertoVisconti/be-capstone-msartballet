package robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO;

import java.util.UUID;

public record InsegnantePubblicoRespDTO(
        UUID id,
        String nome,
        String cognome,
        String imgProfilo,
        String biografia
) {
}
