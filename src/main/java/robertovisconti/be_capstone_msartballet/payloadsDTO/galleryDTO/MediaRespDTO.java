package robertovisconti.be_capstone_msartballet.payloadsDTO.galleryDTO;

import robertovisconti.be_capstone_msartballet.enums.TipoMedia;

import java.util.UUID;

public record MediaRespDTO(
        UUID id,
        String url,
        TipoMedia tipoMedia,
        String titolo,
        UUID idSpettacolo
) {
}
