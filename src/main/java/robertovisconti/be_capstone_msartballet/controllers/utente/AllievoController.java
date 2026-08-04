package robertovisconti.be_capstone_msartballet.controllers.utente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Allievo;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.AllievoRespDTO;
import robertovisconti.be_capstone_msartballet.services.utenti.AllievoService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/utenti/allievi")
public class AllievoController {

    private final AllievoService allievoService;

    public AllievoController(AllievoService allievoService) {
        this.allievoService = allievoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AllievoRespDTO> trovaConFiltri(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) Boolean accountAttivo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate certificatoScadeEntro,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return allievoService.trovaConFiltri(nome, cognome, accountAttivo, certificatoScadeEntro, pageable).map(this::mappa);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AllievoRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(allievoService.trovaPerId(id));
    }

    private AllievoRespDTO mappa(Allievo allievo) {
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
}
