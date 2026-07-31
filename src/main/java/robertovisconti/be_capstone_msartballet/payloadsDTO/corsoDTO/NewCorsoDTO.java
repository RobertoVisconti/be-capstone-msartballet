package robertovisconti.be_capstone_msartballet.payloadsDTO.corsoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import robertovisconti.be_capstone_msartballet.enums.GiornoSettimana;
import robertovisconti.be_capstone_msartballet.enums.LivelloCorso;
import robertovisconti.be_capstone_msartballet.validations.IntervalloOrarioValido;

import java.time.LocalTime;
import java.util.UUID;


@IntervalloOrarioValido(campoInizio = "oraInizio", campoFine = "oraFine")
public record NewCorsoDTO(

        @NotBlank(message = "Il titolo è obbligatorio!")
        String titolo,

        @NotBlank(message = "La descrizione è obbligatoria!")
        String descrizione,

        @NotNull(message = "Il livello del corso è obbligatorio!")
        LivelloCorso livelloCorso,

        @NotNull(message = "Il giorno della settimana è obbligatorio!")
        GiornoSettimana giornoSettimana,

        @NotNull(message = "L'ora d'inizio è obbligatoria!")
        LocalTime oraInizio,

        @NotNull(message = "L'ora di fine è obbligatoria!")
        LocalTime oraFine,

        @NotNull(message = "Il prezzo mensile è obbligatorio!")
        @PositiveOrZero(message = "Il prezzo mensile non può essere negativo!")
        Double prezzoMensile,

        @NotNull(message = "La disciplina è obbligatoria!")
        UUID idDisciplina

) {
}
