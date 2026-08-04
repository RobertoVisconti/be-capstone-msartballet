package robertovisconti.be_capstone_msartballet.controllers.utente;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.entities.*;
import robertovisconti.be_capstone_msartballet.payloadsDTO.uploadDTO.ImmagineRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.CambiaPasswordDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.UtenteMapper;
import robertovisconti.be_capstone_msartballet.services.utenti.UtenteService;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Object trovaProprioProfilo(@AuthenticationPrincipal Utente utente) {
        if (utente instanceof Allievo allievo) {
            return UtenteMapper.mappaAllievo(allievo);
        }
        if (utente instanceof Insegnante insegnante) {
            return UtenteMapper.mappaInsegnante(insegnante);
        }
        if (utente instanceof Ospite ospite) {
            return UtenteMapper.mappaOspite(ospite);
        }
        return UtenteMapper.mappaAdmin((Admin) utente);
    }

    @PostMapping(value = "/me/img-profilo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ImmagineRespDTO caricaImgProfilo(@AuthenticationPrincipal Utente utente, @RequestParam("file") MultipartFile file) {
        String url = utenteService.aggiornaImgProfilo(utente, file);
        return new ImmagineRespDTO(url);
    }

    @PutMapping("/me/password")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cambiaPassword(@AuthenticationPrincipal Utente utente, @RequestBody @Valid CambiaPasswordDTO body) {
        utenteService.cambiaPassword(utente, body);
    }

}
