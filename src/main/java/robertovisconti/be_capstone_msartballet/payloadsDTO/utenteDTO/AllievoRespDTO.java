package robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO;

import robertovisconti.be_capstone_msartballet.enums.LarghezzaPunte;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;

import java.time.LocalDate;
import java.util.UUID;

public record AllievoRespDTO(
        UUID id,
        String nome,
        String cognome,
        String email,
        LocalDate dataDiNascita,
        String imgProfilo,
        RuoloUtente ruolo,
        LocalDate dataRegistrazione,
        String numeroScarpetta,
        String marcaScarpetta,
        Boolean haPunte,
        String marcaPunte,
        LarghezzaPunte larghezzaPunte,
        String tagliaBody,
        String tagliaCalzini,
        Integer altezzaCm,
        String tagliaPantalone,
        LocalDate dataScadenzaCertificato,
        String contattoEmergenzaNome,
        String contattoEmergenzaTelefono,
        String codiceFiscale,
        Boolean quotaIscrizionePagata,
        Boolean consensoPrivacyFoto,
        String noteSegreteria
) {
}
