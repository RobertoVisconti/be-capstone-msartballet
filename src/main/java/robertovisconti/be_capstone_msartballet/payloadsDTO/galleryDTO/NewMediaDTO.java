package robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;
import robertovisconti.be_capstone_msartballet.enums.TipoMedia;

public record NewMediaDTO(

        @NotBlank(message = "L'url è obbligatorio!")
        @URL(message = "Formato url non valido")
        String url,

        @NotNull(message = "Il tipo di media è obbligatorio")
        TipoMedia tipoMedia,

        @NotBlank(message = "Il titolo è obbligatorio!")
        String titolo,

        @NotNull(message = "Lo spettacolo di riferimento è obbligatorio!")
        UUID idSpettacolo
) {
}
