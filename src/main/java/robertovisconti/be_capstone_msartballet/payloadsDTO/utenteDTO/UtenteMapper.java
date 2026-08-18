package robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO;

import robertovisconti.be_capstone_msartballet.entities.Admin;
import robertovisconti.be_capstone_msartballet.entities.Allievo;
import robertovisconti.be_capstone_msartballet.entities.Insegnante;
import robertovisconti.be_capstone_msartballet.entities.Ospite;

public class UtenteMapper {

    private UtenteMapper() {
    }

    public static AllievoRespDTO mappaAllievo(Allievo allievo) {
        return new AllievoRespDTO(
                allievo.getId(),
                allievo.getNome(),
                allievo.getCognome(),
                allievo.getEmail(),
                allievo.getDataDiNascita(),
                allievo.getImgProfilo(),
                allievo.getRuolo(),
                allievo.getDataRegistrazione(),
                allievo.getAccountAttivo(),
                allievo.getNumeroScarpetta(),
                allievo.getMarcaScarpetta(),
                allievo.getHaPunte(),
                allievo.getMarcaPunte(),
                allievo.getLarghezzaPunte(),
                allievo.getTagliaBody(),
                allievo.getTagliaCalzini(),
                allievo.getAltezzaCm(),
                allievo.getTagliaPantalone(),
                allievo.getDataScadenzaCertificato(),
                allievo.getContattoEmergenzaNome(),
                allievo.getContattoEmergenzaTelefono(),
                allievo.getCodiceFiscale(),
                allievo.getQuotaIscrizionePagata(),
                allievo.getConsensoPrivacyFoto(),
                allievo.getNoteSegreteria()
        );
    }

    public static InsegnanteRespDTO mappaInsegnante(Insegnante insegnante) {
        return new InsegnanteRespDTO(
                insegnante.getId(),
                insegnante.getNome(),
                insegnante.getCognome(),
                insegnante.getEmail(),
                insegnante.getDataDiNascita(),
                insegnante.getImgProfilo(),
                insegnante.getRuolo(),
                insegnante.getDataRegistrazione(),
                insegnante.getAccountAttivo(),
                insegnante.getBiografia()
        );
    }

    public static OspiteRespDTO mappaOspite(Ospite ospite) {
        return new OspiteRespDTO(
                ospite.getId(),
                ospite.getNome(),
                ospite.getCognome(),
                ospite.getEmail(),
                ospite.getTelefono(),
                ospite.getDataDiNascita(),
                ospite.getImgProfilo(),
                ospite.getRuolo(),
                ospite.getDataRegistrazione()
        );
    }

    public static AdminRespDTO mappaAdmin(Admin admin) {
        return new AdminRespDTO(
                admin.getId(),
                admin.getNome(),
                admin.getCognome(),
                admin.getEmail(),
                admin.getDataDiNascita(),
                admin.getImgProfilo(),
                admin.getRuolo(),
                admin.getDataRegistrazione(),
                admin.getAccountAttivo()
        );
    }
}
