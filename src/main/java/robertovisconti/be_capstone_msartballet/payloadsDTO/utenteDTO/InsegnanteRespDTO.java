package robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO;

import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;

import java.time.LocalDate;
import java.util.UUID;

public record InsegnanteRespDTO(
        UUID id,
        String nome,
        String cognome,
        String email,
        LocalDate dataDiNascita,
        String imgProfilo,
        RuoloUtente ruolo,
        LocalDate dataRegistrazione,
        String biografia
) {
}
