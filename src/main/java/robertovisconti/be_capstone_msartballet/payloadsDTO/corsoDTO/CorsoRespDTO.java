package robertovisconti.be_capstone_msartballet.payloadsDTO.corsoDTO;

import robertovisconti.be_capstone_msartballet.enums.GiornoSettimana;
import robertovisconti.be_capstone_msartballet.enums.LivelloCorso;

import java.time.LocalTime;
import java.util.UUID;

public record CorsoRespDTO(
        UUID id,
        String titolo,
        String descrizione,
        LivelloCorso livelloCorso,
        GiornoSettimana giornoSettimana,
        LocalTime oraInizio,
        LocalTime oraFine,
        Double prezzoMensile,
        UUID idDisciplina,
        String nomeDisciplina
) {
}
