package robertovisconti.be_capstone_msartballet.payloadsDTO.storeDTO;

import java.util.UUID;

public record ProdottoRespDTO(
        UUID id,
        String titolo,
        String descrizioneProdotto,
        String imgProdotto,
        Double prezzoProdotto
) {
}
