package robertovisconti.be_capstone_msartballet.payloadsDTO.lezioneDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import robertovisconti.be_capstone_msartballet.validations.IntervalloOrarioValido;

import java.time.LocalDateTime;
import java.util.UUID;

@IntervalloOrarioValido(campoInizio = "dataOraInizio", campoFine = "dataOraFine")
public record NewLezioneDTO(

        @NotNull(message = "la data e ora di inizio sono obbligatorie")
        @Future(message = "la lezione deve essere programmata nel futuro")
        LocalDateTime dataOraInizio,

        @NotNull(message = "la data e ora di fine sono obbligatorie")
        @Future(message = "la lezione deve essere programmata nel futuro")
        LocalDateTime dataOraFine,

        @NotNull(message = "il prezzo della lezione è obbligatorio")
        @PositiveOrZero(message = "il prezzo non può essere negativo")
        Double prezzoLezione,

        @NotNull(message = "il corso è obbligatorio")
        UUID idCorso,

        @NotNull(message = "la sala è obbligatoria")
        UUID idSala
) {
}
