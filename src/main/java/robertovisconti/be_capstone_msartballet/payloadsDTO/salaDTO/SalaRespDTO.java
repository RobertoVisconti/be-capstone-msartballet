package robertovisconti.be_capstone_msartballet.payloadsDTO.salaDTO;

import java.util.UUID;

public record SalaRespDTO(
        UUID id,
        String titolo,
        String imgSala,
        Double prezzoAffitto
) {
}
