package robertovisconti.be_capstone_msartballet.controllers.utente;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.payloadsDTO.uploadDTO.ImmagineRespDTO;
import robertovisconti.be_capstone_msartballet.services.utenti.UtenteService;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping(value = "/me/img-profilo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ImmagineRespDTO caricaImgProfilo(@AuthenticationPrincipal Utente utente, @RequestParam("file") MultipartFile file) {
        String url = utenteService.aggiornaImgProfilo(utente, file);
        return new ImmagineRespDTO(url);
    }
}
